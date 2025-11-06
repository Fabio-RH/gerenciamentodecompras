import React, { useState } from "react";
import { View, Text, FlatList, TouchableOpacity, TextInput, StyleSheet } from "react-native";
import { useRouter } from "expo-router";
import {useListas} from "../context/ListasContext"

export default function ListaScreen() {
  const router = useRouter();
  const { listas, adicionarLista, removerLista } = useListas();
  const [nome, setNome] = useState("");

  const criarLista = () => {
    if (nome.trim()) {
      adicionarLista(nome.trim());
      setNome("");
    }
  };

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

      <FlatList
        data={listas}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={styles.listItem}
            onPress={() => router.push(`/lista/${item.id}`)}
          >
            <Text style={styles.listText}>{item.nome}</Text>
            <TouchableOpacity onPress={() => removerLista(item.id)}>
              <Text style={styles.deleteText}>Excluir</Text>
            </TouchableOpacity>
          </TouchableOpacity>
        )}
      />
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
