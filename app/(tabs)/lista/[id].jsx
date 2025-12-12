import React, { useEffect, useState } from "react";
import { View, Text, FlatList, TouchableOpacity, StyleSheet, TextInput, Alert } from "react-native";
import { useLocalSearchParams, useRouter } from "expo-router";
import { useListas } from "../../context/ListasContext";
import api from "@/components/api";
import AsyncStorage from "@react-native-async-storage/async-storage";
import ItemList from "../../../components/item_list";
export default function ListaDetalhe() {
  const router = useRouter();
  const { getLista, toggleItem, excluirItem, adicionarItem } = useListas();
  const [lista, setLista] = useState(null);
  const [listaId, setListaId] = useState(null);
  const [listaNome, setListaNome] = useState(null);

  const loadLista = async () =>{
    const lista_id = await AsyncStorage.getItem("lista_id")
    const lista_nome = await AsyncStorage.getItem("lista_nome")
    const lista_data = await api.get(`/api/item/listar/${lista_id}`)
    console.log(lista_data.data)
    setLista(lista_data.data)
    setListaId(lista_id)
    setListaNome(lista_nome)
  }

  useEffect(() => {


    loadLista();

    }, []);

  const [novoItem, setNovoItem] = useState("");
  const [novoItemQuantidade, setNovoItemQuantidade] = useState("");

  if (!lista) {
    return (
      <View style={styles.container}>
        <Text>Lista não encontrada.</Text>
      </View>
    );
  }

  const handleAddItem = async () => {

    const body1 = {'produto_nome': novoItem, 'produto_categoria': 'produto',
      'produto_unidade_medida': 'padrao', 'produto_status': 1
    }

    const res1 = await api.post(`/api/produto/criar`, body1)
    
    const p_id = res1.data.produto_id
    const body2 = {'item_quantidade' : novoItemQuantidade, 'item_status': 1,
      'lista_id': listaId, 'produto_id' : p_id
    }

    const res2 = await api.post(`/api/item/criar`, body2)

    await loadLista()
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>{lista.nome}</Text>



      {/* Input para adicionar item sem sair da tela */}
      <View style={styles.inputRow}>
        <TextInput
          style={styles.input}
          placeholder="Nome do item"
          value={novoItem}
          onChangeText={setNovoItem}
        />
                <TextInput
          style={styles.input}
          placeholder="Quantidade"
          value={novoItemQuantidade}
          onChangeText={setNovoItemQuantidade}
        />
        

        
      </View>
        <TouchableOpacity style={styles.addButton} onPress={handleAddItem}>
          <Text style={styles.addButtonText}>+</Text>
        </TouchableOpacity>
      <TouchableOpacity
        style={styles.backButton}
        onPress={() => router.back()}
      >
        <Text style={styles.backText}>Voltar</Text>
      </TouchableOpacity>

      <Text style={{textAlign: 'center', marginBottom: 20, fontSize: 25, fontWeight: 800}}> Mostrando lista: "{listaNome}"</Text>
              <ItemList lista={lista} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 20, backgroundColor: "#f9f9f9" },
  title: { fontSize: 26, fontWeight: "bold", color: "#4a2e8e", marginBottom: 20 },
  itemRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    backgroundColor: "#fff",
    padding: 15,
    borderRadius: 8,
    marginBottom: 10,
  },
  itemText: { fontSize: 18 },
  deleteText: { color: "red", fontSize: 18 },
  inputRow: { flexDirection: "column", marginTop: 10 },
  input: {
    flex: 1,
    borderWidth: 1,
    borderColor: "#ccc",
    backgroundColor: "#fff",
    padding: 10,
    borderRadius: 8,
    marginBottom: 10
  },
  addButton: {
    marginLeft: 10,
    backgroundColor: "#4a2e8e",
    borderRadius: 8,
    padding: 10,
    justifyContent: "center",
    alignItems: "center",
  },
  addButtonText: { color: "#fff", fontSize: 18, fontWeight: "bold" },
  backButton: {
    marginTop: 20,
    backgroundColor: "#aaa",
    padding: 12,
    borderRadius: 8,
    alignItems: "center",
    marginBottom: 20,
  },
  backText: { color: "#fff", fontWeight: "bold"},
});
