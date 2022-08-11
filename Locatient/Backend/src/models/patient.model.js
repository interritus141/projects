const mongoose = require("mongoose");
const bcrypt = require("bcrypt");
const bcryptConfig = require("../configs/bcrypt.config");

const patientSchema = new mongoose.Schema({
  nhsid: {
    type: String,
    minLength: 4,
    maxlength: 50,
    required: true,
    index: true,
  },
  firstname: {
    type: String,
    minLength: 1,
    maxlength: 50,
    required: true,
  },
  lastname: {
    type: String,
    minLength: 1,
    maxlength: 50,
    required: true,
  },
  qrhash: {
    type: String,
    maxlength: 80,
    index: true,
  },
});

patientSchema.methods.fullname = function () {
  return `${this.firstname} ${this.lastname}`;
};

patientSchema.pre("save", async function (next) {
  if (this.isNew) {
    let salt = await bcrypt.genSalt(bcryptConfig.saltRound);
    this.qrhash = await bcrypt.hash(this.nhsid, salt);
    next();
  } else {
    next();
  }
});

module.exports = mongoose.model("Patient", patientSchema);
