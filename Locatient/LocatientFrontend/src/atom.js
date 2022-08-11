import { atom } from "recoil";

export const jwtState = atom({
  key: "jwt",
  default: "",
});

export const showExpireState = atom({
  key: "expire",
  default: false,
});

export const userState = atom({
  key: "user",
  default: {
    privilage: "",
    nhsid: "",
  },
});

export const logDataState = atom({
  key: "logs",
  default: {
    logs: [],
  },
});

export const locationDataState = atom({
  key: "locations",
  default: {
    locations: [],
  },
});

export const patientDataState = atom({
  key: "patients",
  default: {
    patients: [],
  },
});

export const favLocationState = atom({
  key: "favoriteLocations",
  default: new Set(),
});

export const favPatientState = atom({
  key: "favoritePatients",
  default: new Set(),
});
