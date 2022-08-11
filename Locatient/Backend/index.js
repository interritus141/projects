const express = require("express");
const app = express();
const path = require("path");
const mongoose = require("mongoose");
const env = require("dotenv");
const serverConfig = require("./src/configs/server.config");
const cors = require("cors");
const {
  authRoute,
  adminRoute,
  logRoute,
  dataRoute,
} = require("./src/routes/route");
const passport = require("passport");
const {
  adminPrivilege,
  hasKey,
  userPrivilege,
} = require("./src/middlewares/permission.middleware");

let envConfigResult = env.config();
if (envConfigResult.error) {
  console.log("!!" + envConfigResult.error);
}

require("./src/configs/passport.config")(passport);
const authorize = require("./src/middlewares/passport.middleware");
const apiAuth = require("./src/middlewares/apikeyauth.middleware");
app.use(express.static(path.join(__dirname, "static")));
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

mongoose
  .connect(process.env.MONGODB_CONNECTION, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => {
    console.log("Connected to mongodb");
  })
  .catch((err) => {
    console.log("!!Connection failed to mongodb:");
    console.log(err);
  });

app.use(
  cors({
    origin: "*",
  })
);
// ----------------------------------------------------

app.use("/api/auth", authRoute);
app.use("/api/admin", authorize, adminPrivilege, adminRoute);
app.use("/api/log", apiAuth, hasKey, logRoute);
app.use("/api/data", authorize, userPrivilege, dataRoute);

// ----------------------------------------------------

app.get("/", (req, res) => {
  res.json({
    message: "API Alive, Connection Successful",
  });
});

app.get("/*", (req, res) => {
  res.status(404);
  res.json({
    error: "Not a valid route",
  });
});

// ----------------------------------------------------
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).send({
    error: "Something went wrong processing the request!",
  });
});
// ----------------------------------------------------
app.listen(serverConfig.port, () => {
  console.log(`Listening on port ${serverConfig.port}`);
});

// module.exports = app;
