import axios from "axios";

const historyService = axios.create({
    baseURL: process.env.HISTORY_SERVICE_URL
});

export default historyService;
