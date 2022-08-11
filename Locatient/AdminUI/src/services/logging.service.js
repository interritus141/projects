import axios from "axios";

const base = "https://api-dot-nhsproject-342615.nw.r.appspot.com";
const API_UPDATE = base.concat("/api/log/update");
const API_LOGS = base.concat("/api/data/log/");

class LocationService {
  addLog(qrhash, roomName, level, date, apiKey) {
    const config = {
      headers: { Authorization: apiKey },
    };
    return axios.post(
      API_UPDATE,
      {
        qrhash,
        roomName,
        level,
        date,
      },
      config
    );
  }

  getLogs(jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.get(API_LOGS, config);
  }
}

export default new LocationService();
