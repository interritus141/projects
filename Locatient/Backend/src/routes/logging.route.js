const router = require("express").Router();
const { Log, Patient, Location } = require("../models/model");
const { logValidation } = require("../validation/log.validation");

router.post("/update/", async (req, res) => {
  const { error } = logValidation(req.body);
  if (error) {
    return res.status("400").json({ error: error.details[0].message });
  }
  const patient = await Patient.findOne({ qrhash: req.body.qrhash });
  const location = await Location.findOne({
    roomName: req.body.roomName,
    level: req.body.level,
  });
  const date = Date.parse(req.body.date);
  /* istanbul ignore next */
  if (date > Date.now()) {
    return res.status("401").json({ error: "Time in the future" });
  }
  /* istanbul ignore next */
  if (!patient) {
    return res.status("401").json({ error: "Cannot find patient" });
  }
  /* istanbul ignore next */
  if (!location) {
    return res.status("401").json({ error: "Cannot find location" });
  }
  const newLog = new Log({
    location: location._id,
    patient: patient._id,
    loggingTime: date,
  });
  try {
    const savedResult = await newLog.save();
    return res.status(200).json({
      message: "Log success",
      saved: savedResult,
    });
  } catch (error) {
    console.log("!!" + error);
    return res.status("500").json({ error: "Save failed" });
  }
});

router.get("/locationList/", async (req, res) => {
  try {
    const locations = await Location.find({});
    return res.status(200).json({
      locations: locations.map((loc) => {
        return {
          roomName: loc.roomName,
          level: loc.level,
        };
      }),
    });
  } catch (error) {
    console.log("!!" + error);
    return res.status("500").json({ error: "Cannot find locations" });
  }
});

module.exports = router;
