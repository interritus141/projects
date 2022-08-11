import React from "react";
import { useState } from "react";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import authService from "../services/auth.service";
import Alert from "@mui/material/Alert";
import Backdrop from "@mui/material/Backdrop";
import CircularProgress from "@mui/material/CircularProgress";
import { jwtState, showExpireState, userState } from "../atom";
import { useRecoilState } from "recoil";

const Login = () => {
  const [usernameField, setUsernameField] = useState("");
  const [passwordField, setPasswordField] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(0);
  const [jwt, setJwt] = useRecoilState(jwtState);
  const [expire, setExpire] = useRecoilState(showExpireState);
  const [user, setUser] = useRecoilState(userState);
  const handleSubmission = function () {
    setLoading(true);
    authService
      .getJwt(usernameField, passwordField)
      .then((d) => {
        if (d && d.data && d.data.message) {
          setMessage(1);
          setExpire(false);
          setUser({
            nhsid: d.data.user.nhsid,
            privilage: d.data.user.privilege,
          });
          localStorage.setItem(
            "user",
            JSON.stringify({
              nhsid: d.data.user.nhsid,
              privilage: d.data.user.privilege,
            })
          );
          setJwt(d.data.signedToken);
          localStorage.setItem("jwt", JSON.stringify(d.data.signedToken));
        } else {
          setMessage(3);
        }
        setLoading(false);
      })
      .catch((e) => {
        if (e.response && e.response.data) {
          setMessage(2);
        } else {
          setMessage(3);
        }
        setLoading(false);
      });
  };
  return (
    <div className="login-page">
      <div className="shape-1">
        <svg
          id="sw-js-blob-svg"
          viewBox="0 0 100 100"
          xmlns="http://www.w3.org/2000/svg"
          version="1.1"
        >
          <defs>
            <linearGradient id="sw-gradient" x1="0" x2="1" y1="1" y2="0">
              <stop
                id="stop1"
                stopColor="rgba(21, 101, 192, 1)"
                offset="0%"
              ></stop>
              <stop
                id="stop2"
                stopColor="rgba(100, 181, 246, 1)"
                offset="100%"
              ></stop>
            </linearGradient>
          </defs>
          <path
            fill="url(#sw-gradient)"
            d="M22.5,-30.6C29.4,-25.9,35.5,-19.7,39.5,-11.8C43.5,-3.9,45.4,5.7,43.3,14.7C41.3,23.6,35.2,31.8,27.3,35.5C19.4,39.3,9.7,38.5,0.1,38.4C-9.5,38.2,-19,38.7,-25.6,34.6C-32.2,30.4,-36.1,21.7,-37.7,13.1C-39.4,4.4,-39,-4,-36.4,-11.7C-33.7,-19.3,-28.8,-26.1,-22.4,-31C-15.9,-35.9,-8,-38.8,-0.1,-38.7C7.8,-38.6,15.5,-35.3,22.5,-30.6Z"
            width="100%"
            height="100%"
            transform="translate(50 50)"
            style={{ transition: "all 0.3s ease 0s" }}
            strokeWidth="0"
          ></path>
        </svg>
      </div>
      <div className="shape-2">
        <svg
          id="sw-js-blob-svg"
          viewBox="0 0 100 100"
          xmlns="http://www.w3.org/2000/svg"
        >
          <defs>
            <linearGradient id="sw-gradient1" x1="0" x2="1" y1="1" y2="0">
              <stop
                id="stop1"
                stopColor="rgba(248, 117, 55, 1)"
                offset="0%"
              ></stop>
              <stop
                id="stop2"
                stopColor="rgba(251, 168, 31, 1)"
                offset="100%"
              ></stop>
            </linearGradient>
          </defs>
          <path
            fill="url(#sw-gradient1)"
            d="M17.9,9.4C12.7,19.4,-9.4,18.8,-15.2,8.5C-21,-1.9,-10.5,-21.9,0.6,-21.6C11.6,-21.3,23.2,-0.6,17.9,9.4Z"
            width="100%"
            height="100%"
            transform="translate(50 50)"
            style={{ transition: "all 0.3s ease 0s" }}
            strokeWidth="0"
          ></path>
        </svg>
      </div>
      <div className="card">
        <div className="login-title">Login</div>
        <div className="meaningful">
          To know even one life has breathed easier because you have lived; that
          is to have succeeded. - Ralph Waldo Emerson
        </div>
        {expire ? (
          <Alert severity="info">Login expired - Please login again</Alert>
        ) : (
          ""
        )}
        {message === 1 ? (
          <Alert severity="success">Login Verified! - Redirecting</Alert>
        ) : message === 2 ? (
          <Alert severity="error">
            Account information incorrect - Try again or contact administrator
          </Alert>
        ) : message === 3 ? (
          <Alert severity="warning">
            Cannot login with the server at this time
          </Alert>
        ) : undefined}
        <TextField
          className="username"
          label="Username"
          variant="outlined"
          value={usernameField}
          onChange={(e) => setUsernameField(e.target.value)}
        />
        <TextField
          className="password"
          label="Password"
          variant="outlined"
          type="password"
          value={passwordField}
          onChange={(e) => setPasswordField(e.target.value)}
        />
        <Button
          variant="contained"
          size="large"
          className="submission"
          onClick={handleSubmission}
        >
          Login
        </Button>
      </div>
      <Backdrop
        sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
        open={loading}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
    </div>
  );
};

export default Login;
