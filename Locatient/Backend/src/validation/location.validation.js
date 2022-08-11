const joi = require("joi");

const locationValidation = (data) => {
  const locationValidator = joi.object({
    roomName: joi.string().min(2).max(50).required(),
    level: joi.number().required(),
  });
  return locationValidator.validate(data);
};

module.exports = {
  locationValidation,
};
