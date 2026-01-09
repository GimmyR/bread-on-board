import type { NextConfig } from "next";

const path = require("path");

require("dotenv").config({
    path: path.resolve(__dirname, '../.env')
});

module.exports = {
    reactStrictMode: true
};

const nextConfig: NextConfig = {
  /* config options here */
};

export default nextConfig;
