import axios from 'axios';

export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

// Central HTTP client: all React requests go through ApiGatewayService.
const apiClient = axios.create({
    baseURL: API_BASE_URL
});

export const FLOWER_API = "/api/flowers";
export const IMAGE_API = "/api/images";
export const INVENTORY_API = "/api/stocks";
export const FLOWERSHOP_API = "/api/flower-shops";
export const USER_API = "/api/users";
export const AUTH_API = "/api/auth";
export const STATISTICS_API = "/api/statistics";
export const NOTIFICATION_API = "/api/notifications";

export default apiClient;
