import axios from "axios";

const base = "https://api-dot-nhsproject-342615.nw.r.appspot.com";
const API_LOGS = base.concat("/api/data/log/");
const API_LOCATIONS = base.concat("/api/data/location/");
const API_PATIENTS = base.concat("/api/data/patient/");

class DataService {
  getLogs(jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.get(API_LOGS, config);
  }
  getLocations(jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.get(API_LOCATIONS, config);
  }
  getPatients(jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.get(API_PATIENTS, config);
  }
}

export default new DataService();
