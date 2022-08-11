module.exports = {
  ...require("./patient.validation"),
  ...require("./unregistration.validation"),
  ...require("./auth.validation"),
  ...require("./qrcodeRequest.validation"),
  ...require("./log.validation"),
  ...require("./location.validation"),
  ...require("./device.validation"),
};
