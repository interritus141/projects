const router = require("express").Router();
const ObjectId = require("mongoose").Types.ObjectId;
const { Log, Patient, Location, Device, User } = require("../models/model");
const { adminPrivilege } = require("../middlewares/permission.middleware");
const qrcode = require("qrcode");

router.get("/device", async (req, res) => {
  try {
    const devices = await Device.find({});
    return res.status(200).json({
      devices: devices.map((d) => {
        let name = d.name;
        let apiKey = d.apiKey;
        return {
          name,
          apiKey,
        };
      }),
    });
  } catch (error) {
    console.log("!!" + error);
    return res.status("500").json({ error: "Cannot find devices" });
  }
});

router.get("/location", async (req, res) => {
  try {
    const location = await Location.find({});
    return res.status(200).json({
      locations: location.map((d) => {
        let roomName = d.roomName;
        let level = d.level;
        return {
          roomName,
          level,
        };
      }),
    });
  } catch (error) {
    console.log("!!" + error);
    return res.status("500").json({ error: "Cannot find locations" });
  }
});

router.get("/patient", async (req, res) => {
  try {
    const patient = await Patient.find({});
    return res.status(200).json({
      patients: await Promise.all(
        patient.map(async (d) => {
          let { nhsid, firstname, lastname, qrhash } = d;
          let code = await qrcode.toDataURL(d.qrhash);
          return {
            nhsid,
            firstname,
            lastname,
            qrhash,
            code,
          };
        })
      ),
    });
  } catch (error) {
    console.log("!!" + error);
    return res.status("500").json({ error: "Cannot find patients" });
  }
});

router.get("/user", adminPrivilege, async (req, res) => {
  try {
    const user = await User.find({});
    return res.status(200).json({
      users: user.map((d) => {
        let { nhsid, password, privilege } = d;
        return {
          nhsid,
          password,
          privilege,
        };
      }),
    });
  } catch (error) {
    console.log("!!" + error);
    return res.status("500").json({ error: "Cannot find users" });
  }
});

router.get("/log/patient/:nhsid", async (req, res) => {
  try {
    const limit = req.query.limit ?? 0;
    const patient = await Patient.findOne({ nhsid: req.params.nhsid });
    if (!patient) {
      return res.status("400").json({ error: "No Such Patient" });
    }

    let logs = await Log.find({ patient: new ObjectId(patient._id) })
      .sort({
        loggingTime: -1,
      })
      .populate("location")
      .limit(limit);
    let { nhsid, firstname, lastname } = patient;
    return res.status(200).json({
      logs: logs.map((log) => {
        let { location, loggingTime } = log;
        return {
          loggingTime,
          roomName: location.roomName,
          level: location.level,
        };
      }),
      patient: {
        nhsid,
        firstname,
        lastname,
      },
    });
  } catch (error) {
    console.log("!!" + error);
    return res.status("500").json({ error: "Cannot find related logs" });
  }
});

router.get("/log/location/:roomName/:level", async (req, res) => {
  try {
    const limit = req.query.limit ?? 0;
    const location = await Location.findOne({
      roomName: req.params.roomName,
      level: req.params.level,
    });
    if (!location) {
      return res.status("400").json({ error: "No Such Location" });
    }

    let logs = await Log.find({ location: new ObjectId(location._id) })
      .sort({
        loggingTime: -1,
      })
      .populate("patient")
      .limit(limit);
    let { roomName, level } = location;
    return res.status(200).json({
      logs: logs.map((log) => {
        let { patient, loggingTime } = log;
        return {
          loggingTime,
          nhsid: patient.nhsid,
          firstname: patient.firstname,
          lastname: patient.lastname,
        };
      }),
      location: {
        roomName,
        level,
      },
    });
  } catch (error) {
    console.log("!!" + error);
    return res.status("500").json({ error: "Cannot find related logs" });
  }
});

router.get("/log/", async (req, res) => {
  try {
    const limit = req.query.limit ?? 0;
    const logs = await Log.find({})
      .sort({ loggingTime: -1 })
      .populate("patient")
      .populate("location")
      .limit(limit);
    return res.status(200).json({
      logs: logs.map((d) => {
        let { nhsid, firstname, lastname } = d.patient;
        let { roomName, level } = d.location;
        return {
          loggingTime: d.loggingTime,
          nhsid,
          firstname,
          lastname,
          roomName,
          level,
        };
      }),
    });
  } catch (error) {
    console.log("!!" + error);
    return res.status("500").json({ error: "Cannot find users" });
  }
});

module.exports = router;
