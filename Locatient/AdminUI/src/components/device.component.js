import React, { useState, useEffect } from "react";
import deviceService from "../services/device.service";
import DataTable from "react-data-table-component";

const DeviceComponent = ({ jwt }) => {
  let [name, setName] = useState("");
  let [deviceApiKey, setDeviceApiKey] = useState("");
  let [context, setContext] = useState("Add");
  let [response, setResponse] = useState("No Requests Made");
  let [devices, setDevices] = useState([]);
  let [clearSelected, setClearSelected] = useState(false);
  const columns = [
    {
      name: "Name",
      selector: (row) => row.name,
      sortable: true,
    },
    {
      name: "API Key",
      selector: (row) => row.apiKey,
    },
  ];

  useEffect(() => {
    deviceService
      .getDevices(jwt)
      .then((d) => {
        setDevices(d.data.devices);
      })
      .catch((error) => {
        console.log(error.response.data);
      });
  }, [jwt]);

  const handleChangeName = (e) => {
    setName(e.target.value);
  };

  const handleChangeApi = (e) => {
    setDeviceApiKey(e.target.value);
  };

  const handleChangeAdd = (e) => {
    if (e.target.checked) setContext("Add");
  };

  const handleChangeDelete = (e) => {
    if (e.target.checked) setContext("Delete");
  };

  const handleSend = () => {
    setClearSelected(true);
    if (context === "Add") {
      deviceService
        .addDevice(name, deviceApiKey, jwt)
        .then((d) => {
          window.alert("Request Successful");
          setResponse(JSON.stringify(d.data, null, 2));
          deviceService
            .getDevices(jwt)
            .then((d) => {
              setDevices(d.data.devices);
            })
            .catch((error) => {
              console.log(error.response.data);
            });
        })
        .catch((error) => {
          window.alert("Bad Request");
          if (error.response && error.response.data) {
            setResponse(JSON.stringify(error.response.data, null, 2));
            deviceService
              .getDevices(jwt)
              .then((d) => {
                setDevices(d.data.devices);
              })
              .catch((error) => {
                console.log(error.response.data);
              });
          } else {
            setResponse("No response");
          }
        });
    } else if (context === "Delete") {
      deviceService
        .deleteDevice(name, deviceApiKey, jwt)
        .then((d) => {
          window.alert("Request Successful");
          setResponse(JSON.stringify(d.data, null, 2));
          deviceService
            .getDevices(jwt)
            .then((d) => {
              setDevices(d.data.devices);
            })
            .catch((error) => {
              console.log(error.response.data);
            });
        })
        .catch((error) => {
          window.alert("Bad Request");
          if (error.response && error.response.data) {
            setResponse(JSON.stringify(error.response.data, null, 2));
          } else {
            setResponse("No response");
          }
        });
    }
  };

  const handleRowSelect = (e) => {
    if (e.selectedCount > 0) {
      setName(e.selectedRows[0].name);
      setDeviceApiKey(e.selectedRows[0].apiKey);
    } else {
      setName("");
      setDeviceApiKey("");
    }
  };

  return (
    <div className="p-5">
      <h1 className="text-center pb-3">Requests</h1>
      <div className="container">
        <div className="row">
          <div className="col">
            <label htmlFor="validationCustom01" className="form-label">
              Name
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom01"
              onChange={handleChangeName}
              value={name}
              required
            />
            <div className="invalid-feedback">Required</div>
          </div>

          <div className="col">
            <label htmlFor="validationCustom02" className="form-label">
              API Key
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom02"
              onChange={handleChangeApi}
              value={deviceApiKey}
              required
            />
            <div className="invalid-feedback">Required</div>
          </div>
        </div>
        <div className="row">
          <div className="col d-flex justify-content-center ">
            <div className="form-check mr-4">
              <input
                className="form-check-input"
                type="radio"
                name="flexRadioDefault"
                id="flexRadioDefault1"
                onClick={handleChangeAdd}
                checked={context === "Add"}
              />
              <label className="form-check-label" htmlFor="flexRadioDefault1">
                Add
              </label>
            </div>
            <div className="form-check">
              <input
                className="form-check-input"
                type="radio"
                name="flexRadioDefault"
                id="flexRadioDefault2"
                onClick={handleChangeDelete}
                checked={context === "Delete"}
              />
              <label className="form-check-label" htmlFor="flexRadioDefault2">
                Delete
              </label>
            </div>
            <div className="invalid-feedback">Required</div>
          </div>
        </div>
        <div className="row text-center">
          <div className="col">
            <button className="btn btn-primary" onClick={handleSend}>
              Send
            </button>
          </div>
        </div>
        <div className="row">
          <div
            className="col m-3"
            style={{
              border: "1px solid rgba(0, 0, 0, .2) ",
              borderRadius: "5px",
            }}
          >
            <h5>Request: </h5>
            <pre>
              <code>{JSON.stringify({ name, deviceApiKey }, null, 2)}</code>
            </pre>
          </div>
          <div
            className="col m-3"
            style={{
              border: "1px solid rgba(0, 0, 0, .2) ",
              borderRadius: "5px",
            }}
          >
            <h5>Response: </h5>
            <pre>
              <code>{response}</code>
            </pre>
          </div>
        </div>
      </div>
      <h1 className="text-center pb-3">Devices</h1>
      <div
        className=""
        style={{
          border: "1px solid rgba(0, 0, 0, .2) ",
          borderRadius: "5px",
        }}
      >
        <DataTable
          columns={columns}
          data={devices}
          pagination
          dense
          selectableRows
          selectableRowsSingle={true}
          clearSelectedRows={clearSelected}
          onSelectedRowsChange={handleRowSelect}
        />
      </div>
    </div>
  );
};

export default DeviceComponent;
