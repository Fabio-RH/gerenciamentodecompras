import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";

const baseURL = "http://localhost:8414";

// =========================
// POST COM TOKEN
// =========================
export const postData = async (route, body) => {
  try {
    const token = await AsyncStorage.getItem("@token");

    const headers = {
      "Content-Type": "application/json",
    };

    if (token) {
      headers["Authorization"] = `Bearer ${token}`;
    }

    const response = await axios.post(`${baseURL}/${route}`, body, { headers });
    console.log("RESPOSTA POST:", response.data);

    return response.data;
  } catch (error) {
    console.error("Erro no POST:", error.response?.data || error.message);
    throw error;
  }
};

// =========================
// LOGIN (ESPECÍFICO)
// =========================
export const login = async (email, senha) => {
  try {
    const body = {
      usuarioEmail: email,    // <-- CORRETO
      usuarioSenha: senha,    // <-- CORRETO
    };

    const response = await axios.post(`${baseURL}/api/usuario/login`, body);

    if (response.data?.usuario_token) {
      await AsyncStorage.setItem("@token", response.data.usuario_token);
    }

    console.log("LOGIN OK", response.data);
    return response.data;

  } catch (error) {
    console.log("ERRO LOGIN:", error.response?.data || error.message);
    return null;
  }
};

// =========================
// GET COM TOKEN (GENÉRICO)
// =========================
export const getData = async (route) => {
  try {
    const token = await AsyncStorage.getItem("@token");

    const headers = {};

    if (token) {
      headers["Authorization"] = `Bearer ${token}`;
    }

    const response = await axios.get(`${baseURL}/${route}`, { headers });
    return response.data;
  } catch (error) {
    console.error("Erro no GET:", error.response?.data || error.message);
    throw error;
  }
};
