const mongoose = require("mongoose");
const bcrypt = require("bcrypt");
const bcryptConfig = require("../configs/bcrypt.config");

const userSchema = new mongoose.Schema({
  nhsid: {
    type: String,
    minLength: 4,
    maxlength: 50,
    required: true,
    index: true,
  },
  password: {
    type: String,
    require: true,
    minLength: 8,
    maxLength: 1024,
  },
  privilege: {
    type: String,
    enum: ["user", "admin"],
    required: true,
  },
});

userSchema.methods.isAdmin = function () {
  return this.privilege == "admin";
};

userSchema.methods.isUser = function () {
  return this.privilege == "user";
};

userSchema.pre("save", async function (next) {
  if (this.isModified("password") || this.isNew) {
    let salt = await bcrypt.genSalt(bcryptConfig.saltRound);
    this.password = await bcrypt.hash(this.password, salt);
    next();
  } else {
    next();
  }
});

userSchema.methods.verifyPassword = function (password, callback) {
  bcrypt.compare(password, this.password, (err, matched) => {
    if (err) {
      console.log("!!" + err);
      callback(err, false);
    }
    callback(null, matched);
  });
};

module.exports = mongoose.model("User", userSchema);
