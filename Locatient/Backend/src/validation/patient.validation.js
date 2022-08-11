const joi = require("joi");

const patientRegistrationValidation = (data) => {
  const registrationValidator = joi.object({
    nhsid: joi.string().min(4).max(50).required(),
    firstname: joi.string().min(1).max(50).required(),
    lastname: joi.string().min(1).max(50).required(),
  });
  return registrationValidator.validate(data);
};

module.exports = {
  patientRegistrationValidation,
};
