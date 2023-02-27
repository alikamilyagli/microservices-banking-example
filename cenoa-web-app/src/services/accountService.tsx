import axios from "axios";

const accountService = axios.create({
    baseURL: process.env.ACCOUNT_SERVICE_URL
});

export default accountService;
