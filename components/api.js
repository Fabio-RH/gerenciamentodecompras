import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";


const a = AsyncStorage;
// const  baseURL =  "http://academico3.rj.senac.br/compras"
const baseURL = 'http://172.26.48.1:8414'

export const postData = async (route, body) => {
    let token = null;
    try {
    const data = await AsyncStorage.getItem("@token");
    await AsyncStorage.setItem("@token", data.usuario_token);
    const o = await AsyncStorage.getItem("@token");
    console.log(a)
    }catch (error) {};


    try {

        const headers = {
      'Content-Type': 'application/json',
    };  
      if (token){
      headers['Authorization'] = `Bearer ${token}`;
      }

      const response = await axios.post(`${baseURL}/${route}`, body, { headers });

      console.log(response.data);
      return response.data;
    } catch (error) {
      console.error('Error posting data:', error);
    }
  };


// export  get (route)
//         const response = await axios.post(`${baseURL}/${route}`, { headers });

// api.interceptors.request.use(async (config) => {
//   const token = await AsyncStorage.getItem("@token");
//   if (token) {
//     config.headers.Authorization = `Bearer ${token}`;
//   }
//   return config;
// });



