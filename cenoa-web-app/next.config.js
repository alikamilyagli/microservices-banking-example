/** @type {import('next').NextConfig} */
const nextConfig = {
  env: {
    ACCOUNT_SERVICE_URL: process.env.ACCOUNT_SERVICE_URL,
    TRANSACTION_SERVICE_URL: process.env.TRANSACTION_SERVICE_URL,
    HISTORY_SERVICE_URL: process.env.HISTORY_SERVICE_URL
  },
  output: 'standalone',
  reactStrictMode: true,
  swcMinify: true,
}

module.exports = nextConfig
