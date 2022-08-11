const mongoose = require("mongoose");

const locationSchema = new mongoose.Schema({
  roomName: {
    type: String,
    minLength: 2,
    maxlength: 50,
    required: true,
  },
  level: {
    type: Number,
    required: true,
  },
});

locationSchema.index({ roomName: 1, level: 1 });

locationSchema.methods.toString = function () {
  return `L${this.Floor}: ${this.roomName}`;
};

module.exports = mongoose.model("Location", locationSchema);
