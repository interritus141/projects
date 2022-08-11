import React, { useState, useEffect } from "react";
import DataTable from "react-data-table-component";
import authService from "../services/auth.service";

const PatientComponent = ({ jwt }) => {
  let [id, setId] = useState("");
  let [password, setPassword] = useState("");
  let [privilege, setPrivilege] = useState("");
  let [context, setContext] = useState("Add");
  let [response, setResponse] = useState("No Requests Made");
  let [users, setUsers] = useState([]);
  let [clearSelected, setClearSelected] = useState(false);
  const columns = [
    {
      name: "ID",
      selector: (row) => row.nhsid,
    },
    {
      name: "Password",
      selector: (row) => row.password,
      sortable: true,
    },
    {
      name: "Privilege",
      selector: (row) => row.privilege,
      sortable: true,
    },
  ];

  useEffect(() => {
    authService
      .getUsers(jwt)
      .then((d) => {
        setUsers(d.data.users);
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
      authService
        .addUser(id, password, privilege, jwt)
        .then((d) => {
          window.alert("Request Successful");
          setResponse(JSON.stringify(d.data, null, 2));
          authService
            .getUsers(jwt)
            .then((d) => {
              setUsers(d.data.users);
            })
            .catch((error) => {
              alert(JSON.stringify(error.response.data, null, 2));
            });
        })
        .catch((error) => {
          window.alert("Bad Request");
          if (error.response && error.response.data) {
            setResponse(JSON.stringify(error.response.data, null, 2));
            authService
              .getUsers(jwt)
              .then((d) => {
                setUsers(d.data.users);
              })
              .catch((error) => {
                alert(JSON.stringify(error.response.data, null, 2));
              });
          } else {
            setResponse("No response");
          }
        });
    } else if (context === "Delete") {
      authService
        .deleteUser(id, jwt)
        .then((d) => {
          window.alert("Request Successful");
          setResponse(JSON.stringify(d.data, null, 2));
          authService
            .getUsers(jwt)
            .then((d) => {
              setUsers(d.data.users);
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
      setPassword(e.selectedRows[0].password);
      setPrivilege(e.selectedRows[0].privilege);
    } else {
      setPassword("");
      setPrivilege("");
    }
  };

  return (
    <div className="p-5">
      <h1 className="text-center pb-3">Requests</h1>
      <div className="container">
        <div className="row">
          <div className="col">
            <label htmlFor="validationCustom02" className="form-label">
              ID
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom02"
              onChange={handleInputs(id, setId)}
              value={id}
              required
            />
            <div className="invalid-feedback">Required</div>
          </div>

          <div className="col">
            <label htmlFor="validationCustom02" className="form-label">
              Password
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom02"
              onChange={handleInputs(password, setPassword)}
              value={password}
              required
            />
            <div className="invalid-feedback">Required</div>
          </div>
        </div>
        <div className="row">
          <div className="col">
            <label htmlFor="validationCustom01" className="form-label">
              Privilege
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom01"
              onChange={handleInputs(privilege, setPrivilege)}
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
                {JSON.stringify({ id, password, privilege }, null, 2)}
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
      <h1 className="text-center pb-3">Users</h1>
      <div
        className=""
        style={{
          border: "1px solid rgba(0, 0, 0, .2) ",
          borderRadius: "5px",
        }}
      >
        <DataTable
          columns={columns}
          data={users}
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

export default PatientComponent;
