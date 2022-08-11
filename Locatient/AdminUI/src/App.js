import React, { useState } from "react";
import { Routes, Route } from "react-router-dom";
import NavComponent from "./components/nav.component";
import PageNotFound from "./components/pageNotFound.component";
import ProfileComponent from "./components/profile.component";
import DeviceComponent from "./components/device.component";
import LocationComponent from "./components/location.component";
import LoggingComponent from "./components/logging.component";
import PatientComponent from "./components/patient.component";
import UserComponent from "./components/user.component";

function App() {
  let [username, setUsername] = useState("");
  let [password, setPassword] = useState("");
  let [apiToken, setApiToken] = useState("");
  let [jwt, setJwt] = useState("");

  return (
    <div>
      <NavComponent />
      <Routes>
        <Route
          exact
          path="/"
          element={
            <ProfileComponent
              username={username}
              setUsername={setUsername}
              password={password}
              setPassword={setPassword}
              apiToken={apiToken}
              setApiToken={setApiToken}
              jwt={jwt}
              setJwt={setJwt}
            />
          }
        />
        <Route
          exact
          path="/api/devices"
          element={<DeviceComponent jwt={jwt} />}
        />
        <Route
          exact
          path="/api/locations"
          element={<LocationComponent jwt={jwt} />}
        />
        <Route
          exact
          path="/api/logging"
          element={<LoggingComponent jwt={jwt} apiKey={apiToken} />}
        />
        <Route
          exact
          path="/api/patients"
          element={<PatientComponent jwt={jwt} />}
        />
        <Route exact path="/api/users" element={<UserComponent jwt={jwt} />} />
        <Route element={<PageNotFound />} />
      </Routes>
    </div>
  );
}

export default App;
