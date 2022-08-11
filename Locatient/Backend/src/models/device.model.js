const mongoose = require("mongoose");

const deviceSchema = new mongoose.Schema({
  name: {
    type: String,
    minLength: 2,
    maxlength: 50,
    required: true,
  },
  apiKey: {
    type: String,
    minLength: 2,
    maxlength: 50,
    required: true,
    index: true,
  },
});

module.exports = mongoose.model("Device", deviceSchema);
