import React, { useEffect, useState } from "react";
import { View, Text, FlatList, TouchableOpacity, TextInput, StyleSheet } from "react-native";
import { useRouter } from "expo-router";
import {useListas} from "../context/ListasContext"
import ListaList from '../../components/lista_list'
import api from "@/components/api";
import AsyncStorage from "@react-native-async-storage/async-storage";
export default function ListaScreen() {
  const router = useRouter();
  const { listas, adicionarLista, removerLista } = useListas();
  const [dataList, setDataList] = useState([]);
  const [nome, setNome] = useState("");



  const loadListas = async () =>{
      const id = await AsyncStorage.getItem('id')
      const res = await api.get(`/api/lista/listarPorUsuario/${id}`)
      setDataList(res.data)
  }

const abrirLista = async (lista_id, lista_nome) => {
  try {
    await AsyncStorage.setItem('lista_id', lista_id);
    await AsyncStorage.setItem('lista_nome', lista_nome);
    router.replace("/lista/index"); 
  } catch (err) {
    console.log("Erro ao salvar lista_id:" ,err);
  }
};



  const criarLista = async () => {
    try{
      const id = await AsyncStorage.getItem('id')
      const dataAtual = new Date();
      const formatoISO = dataAtual.toISOString().split('.')[0];

      const body = {
        "lista_nome" : nome, "lista_dataCriacao" : formatoISO,
        "lista_status": 1, "usuario_id" : id
      }
      const res = await api.post(`/api/lista/criar`, body)
      console.log("função de criar lista: " ,res)
      await loadListas()
    }
    catch(error) {console.log(error)}

  };

 useEffect(() => {


  loadListas();

  console.log(dataList)
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Minhas Listas</Text>

      <TextInput
        style={styles.input}
        placeholder="Nova lista"
        value={nome}
        onChangeText={setNome}
      />

      <TouchableOpacity style={styles.button} onPress={criarLista}>
        <Text style={styles.buttonText}>Adicionar Lista</Text>
      </TouchableOpacity>

      <ListaList OnPress={abrirLista} lista={dataList} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 20, backgroundColor: "#f9f9f9" },
  title: { fontSize: 26, fontWeight: "bold", color: "#4a2e8e", marginBottom: 20 },
  input: {
    borderWidth: 1,
    borderColor: "#ccc",
    backgroundColor: "#fff",
    padding: 10,
    borderRadius: 8,
    marginBottom: 10,
  },
  button: {
    backgroundColor: "#4a2e8e",
    padding: 10,
    borderRadius: 8,
    alignItems: "center",
    marginBottom: 20,
  },
  buttonText: { color: "#fff", fontSize: 16, fontWeight: "bold" },
  listItem: {
    flexDirection: "row",
    justifyContent: "space-between",
    padding: 15,
    backgroundColor: "#e7d9ff",
    borderRadius: 8,
    marginBottom: 10,
  },
  listText: { fontSize: 18, fontWeight: "bold" },
  deleteText: { color: "red", fontWeight: "bold" },
});
