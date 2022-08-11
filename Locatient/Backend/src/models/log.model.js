const mongoose = require("mongoose");
const logConfig = require("../configs/logging.config");

const logSchema = new mongoose.Schema({
  location: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Location",
    required: true,
  },
  patient: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Patient",
    required: true,
  },
  loggingTime: {
    type: Date,
    required: true,
  },
});

logSchema.index({ loggingTime: -1 }, { expireAfterSeconds: logConfig.expires });
logSchema.index({ patient: 1, loggingTime: -1 });
logSchema.index({ location: 1, loggingTime: -1 });

module.exports = mongoose.model("Log", logSchema);
