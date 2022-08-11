import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./components/login.component";
import { useEffect, useState } from "react";
import Home from "./components/home.component";
import Top from "./components/top.component";
import Sidebar from "./components/sidebar.component";
import Patients from "./components/allPatient.component";
import Locations from "./components/allLocation.component";
import Analytics from "./components/analytics.component";
import Logs from "./components/log.component";
import Patient from "./components/patient.component";
import Location from "./components/location.component";
import Favorites from "./components/favorites.component";
import { useRecoilState } from "recoil";
import {
  favLocationState,
  favPatientState,
  jwtState,
  locationDataState,
  logDataState,
  patientDataState,
  showExpireState,
  userState,
} from "./atom";
import dataService from "./services/data.service";
import HomeIcon from "@mui/icons-material/Home";

function App() {
  const [toggle, setToggle] = useState(false);
  const [jwt, setJwt] = useRecoilState(jwtState);
  const [user, setUser] = useRecoilState(userState);
  const [logs, setLogs] = useRecoilState(logDataState);
  const [locations, setLocations] = useRecoilState(locationDataState);
  const [patients, setPatients] = useRecoilState(patientDataState);
  const [expire, setExpire] = useRecoilState(showExpireState);
  const [favoriteLocations, setFavoriteLocations] =
    useRecoilState(favLocationState);
  const [favoritePatients, setFavoritePatients] =
    useRecoilState(favPatientState);

  const handleToggle = () => {
    if (toggle) {
      setToggle("");
      setTimeout(() => {
        setToggle(false);
      }, 500);
    } else if (toggle === false) {
      setToggle(true);
    }
  };
  useEffect(() => {
    function syncLocal() {
      let localJwt = JSON.parse(localStorage.getItem("jwt"));
      if (localJwt) setJwt(localJwt);
      let localUser = JSON.parse(localStorage.getItem("user"));
      if (localUser) setUser(localUser);
      let localFavP = JSON.parse(localStorage.getItem("favoritePatients"));
      if (localFavP) setFavoritePatients(new Set(localFavP));
      let localFavL = JSON.parse(localStorage.getItem("favoriteLocations"));
      if (localFavL) setFavoriteLocations(new Set(localFavL));
    }

    syncLocal();
  }, []);

  useEffect(() => {
    function fetchData() {
      dataService
        .getLogs(jwt)
        .then((d) => {
          setLogs({ logs: d.data.logs });
        })
        .catch((e) => {
          if (e.response && e.response.data) {
            if (e.response.status == 401) {
              setJwt("");
              localStorage.removeItem("jwt");
              setExpire(true);
            } else {
              console.log("error 2", { ...e }, e);
            }
          } else {
            console.log("error 3", { ...e }, e);
          }
        });
      dataService
        .getLocations(jwt)
        .then((d) => {
          setLocations({ locations: d.data.locations });
        })
        .catch((e) => {
          if (e.response && e.response.data) {
            if (e.response.status == 401) {
              setJwt("");
              localStorage.removeItem("jwt");
              setExpire(true);
            } else {
              console.log("error 4", { ...e }, e);
            }
          } else {
            console.log("error 5", { ...e }, e);
          }
        });
      dataService
        .getPatients(jwt)
        .then((d) => {
          setPatients({ patients: d.data.patients });
        })
        .catch((e) => {
          if (e.response && e.response.data) {
            if (e.response.status == 401) {
              setJwt("");
              localStorage.removeItem("jwt");
              setExpire(true);
            } else {
              console.log("error 6", { ...e }, e);
            }
          } else {
            console.log("error 7", { ...e }, e);
          }
        });
    }
    if (jwt) {
      fetchData();
    }
  }, [jwt]);

  if (jwt === "") {
    return <Login />;
  }

  return (
    <div className="App">
      <BrowserRouter>
        <div className="panels">
          <Sidebar key={toggle} toggle={toggle} />
          <div className="toggle-btn">
            <HomeIcon onClick={handleToggle} />
          </div>
          <div className="content">
            <Top />
            <div className="content-box">
              <Routes>
                <Route exact path="/" element={<Home />}></Route>
                <Route exact path="/analytics" element={<Analytics />}></Route>
                <Route exact path="/logs" element={<Logs />}></Route>
                <Route
                  exact
                  path="/patient_activities"
                  element={<Patient />}
                ></Route>
                <Route
                  exact
                  path="/location_activities"
                  element={<Location />}
                ></Route>
                <Route exact path="/favorites" element={<Favorites />}></Route>
                <Route exact path="/patients" element={<Patients />}></Route>
                <Route exact path="/locations" element={<Locations />}></Route>
              </Routes>
            </div>
          </div>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
