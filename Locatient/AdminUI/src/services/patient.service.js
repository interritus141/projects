import axios from "axios";
const base = "https://api-dot-nhsproject-342615.nw.r.appspot.com";
const API_PATIENT_REGISTER = base.concat("/api/admin/registerPatient");
const API_PATIENT_REMOVE = base.concat("/api/admin/unregister");
const API_PATIENT_GET = base.concat("/api/data/patient");

class PatientService {
  registerPatient(nhsid, firstname, lastname, jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.post(
      API_PATIENT_REGISTER,
      {
        nhsid,
        firstname,
        lastname,
      },
      config
    );
  }

  deletePatient(nhsid, jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.post(
      API_PATIENT_REMOVE,
      {
        nhsid,
        type: "patient",
      },
      config
    );
  }

  getPatients(jwt, limit = 0) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.get(API_PATIENT_GET, config);
  }
}

export default new PatientService();
