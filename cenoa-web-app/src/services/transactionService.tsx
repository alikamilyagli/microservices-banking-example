import axios from "axios";

const transactionService = axios.create({
    baseURL: process.env.TRANSACTION_SERVICE_URL
});

export default transactionService;
