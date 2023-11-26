package com.example.novascotiawrittendrivingtest

class ValidateRegister {
    companion object {
        /**
         * These methods check if the register password is validated or not
         * @param pwd the password that read from register format
         * @return true if the password contains at least one capital letter, one lower case, one symbol,
         *         and the length is less than 13 and more than 8
         */
        fun validatePwdNullEmpty(pwd: String?): Boolean {
            return !pwd.isNullOrEmpty()
        }

        /**
         * This method checks if the register password has the right length
         * @param pwd the password that read from register format
         * @return true if the password is between 8 -20, false if less than 8 or more than 20
         */
        fun validatePwdLength(pwd: String): Boolean {
            return pwd.length in 8..20
        }

        /**
         * This method checks if the register password's format
         * @param pwd the password that read from register format
         * @return true if the password is following the right format (match the format: has capital and lower letters at the same time, also with numbers and symbols)
         * false if any of the requirements are missing
         */
        fun validatePwdFormat(pwd: String): Boolean {
            return pwd.matches(".*[A-Z].*".toRegex()) &&
                    pwd.matches(".*[a-z].*".toRegex()) &&
                    pwd.matches(".*[0-9].*".toRegex())
        }

        /**
         * This method checks the register email's format
         * @param email the email that read from register format
         * @return true if the email fits the format that has a @ symbol in string and a dot symbol at last
         */
        fun validateEmail(email: String): Boolean {
            val emailRegex = "^([a-zA-Z0-9]*[-_]?[.]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$"
            return email.matches(emailRegex.toRegex())
        }

        /**
         * This method checks if the register email's format is right or not
         * @param email the email that read from register format
         * @return true if the email is not null, false if the email is null
         */
        fun validateEmailNullEmpty(email: String?): Boolean {
            return !email.isNullOrEmpty()
        }
    }
}