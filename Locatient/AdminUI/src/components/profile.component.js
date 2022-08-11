import React from "react";
import authService from "../services/auth.service";

const ProfileComponent = (props) => {
  let {
    username,
    setUsername,
    password,
    setPassword,
    apiToken,
    setApiToken,
    jwt,
    setJwt,
  } = props;

  const handleInputs = (get, set) => {
    return (e) => {
      return set(e.target.value);
    };
  };
  const getJWT = () => {
    authService
      .getJwt(username, password)
      .then((d) => {
        setJwt(d.data.signedToken);
      })
      .catch((error) => {
        alert(error.response && JSON.stringify(error.response.data));
      });
  };

  return (
    <div className="p-5">
      <h1 className="pb-3 text-center">Profile</h1>
      <form
        className="container g-3 needs-validation"
        autoComplete="off"
        noValidate
      >
        <div className="row">
          <div className="col">
            <label htmlFor="validationCustom01" className="form-label">
              Username / ID
            </label>
            <input
              type="text"
              className="form-control"
              id="validationCustom01"
              onChange={handleInputs(username, setUsername)}
              value={username}
              required
            />
            <div className="invalid-feedback">Required</div>
          </div>
          <div className="col">
            <label htmlFor="validationCustom02" className="form-label">
              Password
            </label>
            <input
              type="password"
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
          <div className="col ">
            <label htmlFor="validationCustomUsername" className="form-label">
              JWT Token
            </label>
            <div className="input-group has-validation">
              <span className="input-group-text" id="inputGroupPrepend">
                JWT
              </span>
              <input
                type="text"
                className="form-control"
                id="validationCustomUsername"
                aria-describedby="inputGroupPrepend"
                onChange={handleInputs(jwt, setJwt)}
                value={jwt}
                disabled
              />
            </div>
          </div>
          <div className="col-md-2 col-sm-4 d-flex ">
            <button
              className="btn btn-primary mt-md-4 mt-sm-2 mt-1"
              type="button"
              onClick={getJWT}
            >
              Request Token
            </button>
          </div>
        </div>
        <div className="row">
          <div className="col ">
            <label htmlFor="validationCustomUsername" className="form-label">
              Logging API Token
            </label>
            <div className="input-group has-validation">
              <span className="input-group-text" id="inputGroupPrepend">
                Devices
              </span>
              <input
                type="text"
                className="form-control"
                id="validationCustomUsername"
                aria-describedby="inputGroupPrepend"
                onChange={handleInputs(apiToken, setApiToken)}
                value={apiToken}
              />
            </div>
          </div>
        </div>
      </form>
    </div>
  );
};

export default ProfileComponent;
