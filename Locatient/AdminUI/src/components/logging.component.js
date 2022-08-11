import React, { useState, useEffect } from "react";
import DataTable from "react-data-table-component";
import loggingService from "../services/logging.service";
import patientService from "../services/patient.service";
import locationService from "../services/location.service";

const LoggingComponent = ({ jwt, apiKey }) => {
  let [patient, setPatient] = useState(undefined);
  let [location, setLocation] = useState(undefined);
  let [response, setResponse] = useState("No Requests Made");
  let [patients, setPatients] = useState([]);
  let [locations, setLocations] = useState([]);
  let [logs, setLogs] = useState([]);
  const columns = [
    {
      name: "Time",
      selector: (row) => row.loggingTime,
      sortable: true,
    },
    {
      name: "Patient ID",
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
    {
      name: "Room Name",
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
    patientService
      .getPatients(jwt)
      .then((d) => {
        setPatients(d.data.patients);
      })
      .catch((error) => {
        alert(JSON.stringify(error.response.data, null, 2));
      });

    locationService
      .getLocations(jwt)
      .then((d) => {
        setLocations(d.data.locations);
      })
      .catch((error) => {
        alert(JSON.stringify(error.response.data, null, 2));
      });
    loggingService
      .getLogs(jwt)
      .then((d) => {
        setLogs(d.data.logs);
      })
      .catch((error) => {
        alert(JSON.stringify(error.response.data, null, 2));
      });
  }, [jwt]);

  const handleSelects = (arr, set) => {
    return (e) => {
      return set(arr[e.target.value]);
    };
  };

  const handleSend = () => {
    if (!patient || !location) {
      alert("fields cannot be empty");
      return;
    }
    let time = new Date().toISOString();
    loggingService
      .addLog(patient.qrhash, location.roomName, location.level, time, apiKey)
      .then((d) => {
        window.alert("Request Successful");
        setResponse(JSON.stringify(d.data, null, 2));
        loggingService
          .getLogs(jwt)
          .then((d) => {
            setLogs(d.data.logs);
          })
          .catch((error) => {
            alert(JSON.stringify(error.response.data, null, 2));
          });
      })
      .catch((error) => {
        window.alert("Bad Request");
        if (error.response && error.response.data) {
          setResponse(JSON.stringify(error.response.data, null, 2));
          loggingService
            .getPatients(jwt)
            .then((d) => {
              setLogs(d.data.logs);
            })
            .catch((error) => {
              alert(JSON.stringify(error.response.data, null, 2));
            });
        } else {
          setResponse("No response");
        }
      });
  };

  return (
    <div className="p-5">
      <h1 className="text-center pb-3">Requests</h1>
      <div className="container">
        <div className="row">
          <div className="col">
            <label htmlFor="validationCustom01" className="form-label">
              Patient
            </label>
            <select
              id="validationCustom01"
              className="form-control "
              onChange={handleSelects(patients, setPatient)}
              required
            >
              <option value="-1">None</option>
              {patients.map((p, i) => {
                return (
                  <option value={i} key={i}>
                    {p.nhsid} # {p.firstname} {p.lastname}
                  </option>
                );
              })}
            </select>
            <div className="invalid-feedback">Required</div>
          </div>

          <div className="col">
            <label htmlFor="validationCustom02" className="form-label">
              Location
            </label>
            <select
              id="validationCustom02"
              className="form-control"
              onChange={handleSelects(locations, setLocation)}
              required
            >
              <option value="-1">None</option>
              {locations.map((l, i) => {
                return (
                  <option value={i} key={i}>
                    {l.level} - {l.roomName}
                  </option>
                );
              })}
            </select>
            <div className="invalid-feedback">Required</div>
          </div>
        </div>
        <div className="row text-center">
          <div className="col">
            <button className="btn btn-primary mt-2" onClick={handleSend}>
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
                {JSON.stringify(
                  { patient, location, date: "current" },
                  null,
                  2
                )}
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
      <h1 className="text-center pb-3">Logs</h1>
      <div
        className=""
        style={{
          border: "1px solid rgba(0, 0, 0, .2) ",
          borderRadius: "5px",
        }}
      >
        <DataTable columns={columns} data={logs} pagination dense />
      </div>
    </div>
  );
};

export default LoggingComponent;
