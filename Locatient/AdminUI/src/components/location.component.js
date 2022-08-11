import React, { useState, useEffect } from "react";
import DataTable from "react-data-table-component";
import locationService from "../services/location.service";

const LocationComponent = ({ jwt }) => {
  let [roomName, setRoomName] = useState("");
  let [level, setLevel] = useState("");
  let [context, setContext] = useState("");
  let [response, setResponse] = useState("No Requests Made");
  let [locations, setLocations] = useState([]);
  let [clearSelected, setClearSelected] = useState(false);
  const columns = [
    {
      name: "RoomName",
      selector: (row) => row.roomName,
      sortable: true,
    },
    {
      name: "Level",
      selector: (row) => row.level,
      sortable: true,
    },
  ];

  useEffect(() => {
    locationService
      .getLocations(jwt)
      .then((d) => {
        setLocations(d.data.locations);
      })
      .catch((error) => {
        alert(JSON.stringify(error.response.data, null, 2));
      });
  }, [jwt]);

  const handleInputs = (get, set) => {
    return (e) => {
      return set(e.target.value);
    };
  };

  const handleChangeAdd = (e) => {
    if (e.target.checked) {
      setContext("Add");
    }
  };

  const handleChangeDelete = (e) => {
    if (e.target.checked) {
      setContext("Delete");
    }
  };

  const handleSend = () => {
    setClearSelected(true);
    if (context === "Add") {
      locationService
        .addLocation(roomName, level, jwt)
        .then((d) => {
          window.alert("Request Successful");
          setResponse(JSON.stringify(d.data, null, 2));
          locationService
            .getLocations(jwt)
            .then((d) => {
              setLocations(d.data.locations);
            })
            .catch((error) => {
              alert(JSON.stringify(error.response.data, null, 2));
            });
        })
        .catch((error) => {
          window.alert("Bad Request");
          if (error.response && error.response.data) {
            setResponse(JSON.stringify(error.response.data, null, 2));
            locationService
              .getLocations(jwt)
              .then((d) => {
                setLocations(d.data.locations);
              })
              .catch((error) => {
                alert(JSON.stringify(error.response.data, null, 2));
              });
          } else {
            setResponse("No response");
          }
        });
    } else if (context === "Delete") {
      locationService
        .deleteLocation(roomName, level, jwt)
        .then((d) => {
          window.alert("Request Successful");
          setResponse(JSON.stringify(d.data, null, 2));
          locationService
            .getLocations(jwt)
            .then((d) => {
              setLocations(d.data.locations);
            })
            .catch((error) => {
              alert(JSON.stringify(error.response.data, null, 2));
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
      setRoomName(e.selectedRows[0].roomName);
      setLevel(e.selectedRows[0].level);
    } else {
      setRoomName("");
      setLevel("");
    }
  };

  return (
    <div className="p-5">
      <h1 className="text-center pb-3">Requests</h1>
      <div className="container">
        <div className="row">
          <div className="col">
            <label htmlFor="validationCustom01" className="form-label">
              Room Name
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom01"
              onChange={handleInputs(roomName, setRoomName)}
              value={roomName}
              required
            />
            <div className="invalid-feedback">Required</div>
          </div>

          <div className="col">
            <label htmlFor="validationCustom02" className="form-label">
              Level
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom02"
              onChange={handleInputs(level, setLevel)}
              value={level}
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
              <code>{JSON.stringify({ roomName, level }, null, 2)}</code>
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
      <h1 className="text-center pb-3">Locations</h1>
      <div
        className=""
        style={{
          border: "1px solid rgba(0, 0, 0, .2) ",
          borderRadius: "5px",
        }}
      >
        <DataTable
          columns={columns}
          data={locations}
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

export default LocationComponent;
