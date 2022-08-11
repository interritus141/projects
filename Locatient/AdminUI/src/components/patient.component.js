import React, { useState, useEffect } from "react";
import DataTable from "react-data-table-component";
import patientService from "../services/patient.service";

const PatientComponent = ({ jwt }) => {
  let [id, setId] = useState("");
  let [firstname, setFirstname] = useState("");
  let [lastname, setLastname] = useState("");
  let [context, setContext] = useState("Add");
  let [response, setResponse] = useState("No Requests Made");
  let [patients, setPatients] = useState([]);
  let [clearSelected, setClearSelected] = useState(false);
  const columns = [
    {
      name: "ID",
      selector: (row) => row.nhsid,
    },
    {
      name: "Firstname",
      selector: (row) => row.firstname,
      sortable: true,
    },
    {
      name: "Lastname",
      selector: (row) => row.lastname,
      sortable: true,
    },
  ];

  const ExpandedComponent = ({ data }) => {
    return (
      <div className="d-flex justify-content-center">
        <img src={data.code} alt="qrcode" />
      </div>
    );
  };
  useEffect(() => {
    patientService
      .getPatients(jwt)
      .then((d) => {
        setPatients(d.data.patients);
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
    if (e.target.checked) setContext("Add");
  };

  const handleChangeDelete = (e) => {
    if (e.target.checked) setContext("Delete");
  };

  const handleSend = () => {
    setClearSelected(true);
    if (context === "Add") {
      patientService
        .registerPatient(id, firstname, lastname, jwt)
        .then((d) => {
          window.alert("Request Successful");
          setResponse(JSON.stringify(d.data, null, 2));
          patientService
            .getPatients(jwt)
            .then((d) => {
              setPatients(d.data.patients);
            })
            .catch((error) => {
              alert(JSON.stringify(error.response.data, null, 2));
            });
        })
        .catch((error) => {
          window.alert("Bad Request");
          if (error.response && error.response.data) {
            setResponse(JSON.stringify(error.response.data, null, 2));
            patientService
              .getPatients(jwt)
              .then((d) => {
                setPatients(d.data.patients);
              })
              .catch((error) => {
                alert(JSON.stringify(error.response.data, null, 2));
              });
          } else {
            setResponse("No response");
          }
        });
    } else if (context === "Delete") {
      patientService
        .deletePatient(id, jwt)
        .then((d) => {
          window.alert("Request Successful");
          setResponse(JSON.stringify(d.data, null, 2));
          patientService
            .getPatients(jwt)
            .then((d) => {
              setPatients(d.data.patients);
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
      setId(e.selectedRows[0].nhsid);
      setFirstname(e.selectedRows[0].firstname);
      setLastname(e.selectedRows[0].lastname);
    } else {
      setFirstname("");
      setLastname("");
    }
  };

  return (
    <div className="p-5">
      <h1 className="text-center pb-3">Requests</h1>
      <div className="container">
        <div className="row">
          <div className="col">
            <label htmlFor="validationCustom02" className="form-label">
              Firstname
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom02"
              onChange={handleInputs(firstname, setFirstname)}
              value={firstname}
              required
            />
            <div className="invalid-feedback">Required</div>
          </div>

          <div className="col">
            <label htmlFor="validationCustom02" className="form-label">
              Lastname
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom02"
              onChange={handleInputs(lastname, setLastname)}
              value={lastname}
              required
            />
            <div className="invalid-feedback">Required</div>
          </div>
        </div>
        <div className="row">
          <div className="col">
            <label htmlFor="validationCustom01" className="form-label">
              ID
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom01"
              onChange={handleInputs(id, setId)}
              value={id}
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
              <code>
                {JSON.stringify({ id, firstname, lastname }, null, 2)}
              </code>
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
      <h1 className="text-center pb-3">Patients</h1>
      <div
        className=""
        style={{
          border: "1px solid rgba(0, 0, 0, .2) ",
          borderRadius: "5px",
        }}
      >
        <DataTable
          columns={columns}
          data={patients}
          pagination
          dense
          selectableRows
          selectableRowsSingle={true}
          clearSelectedRows={clearSelected}
          onSelectedRowsChange={handleRowSelect}
          expandableRows
          expandableRowsComponent={ExpandedComponent}
        />
      </div>
    </div>
  );
};

export default PatientComponent;
