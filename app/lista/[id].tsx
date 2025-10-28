import { useLocalSearchParams, useRouter } from "expo-router";
import { useState } from "react";
import {
  Alert,
  FlatList,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { useListas } from "../../context/ListasContext";

export default function ListaDetalhes() {
  const router = useRouter();
  const { id } = useLocalSearchParams<{ id: string }>();
  const { listas, excluirItem, toggleItem } = useListas();

  const lista = listas.find((l) => l.id === id);

  if (!lista) {
    return (
      <View style={styles.container}>
        <Text style={styles.text}>Lista não encontrada.</Text>
        <TouchableOpacity
          style={[styles.addButton, { backgroundColor: "#999" }]}
          onPress={() => router.push("/")}
        >
          <Text style={styles.addText}>Voltar para Home</Text>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>{lista.nome}</Text>

      <FlatList
        data={lista.itens}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={styles.item}
            onPress={() => toggleItem?.(lista.id, item.id)}
          >
            <Text style={[styles.text, item.comprado && styles.comprado]}>
              {item.nome}
            </Text>
            <View style={styles.actions}>
              <TouchableOpacity
                onPress={() =>
                  router.push({
                    pathname: "/lista/novo-item",
                    params: {
                      listaId: lista.id,
                      itemId: item.id,
                      nome: item.nome,
                    },
                  })
                }
              >
                <Text style={styles.edit}>Editar</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={() => excluirItem?.(lista.id, item.id)}>
                <Text style={styles.delete}>Excluir</Text>
              </TouchableOpacity>
            </View>
          </TouchableOpacity>
        )}
      />

      <TouchableOpacity
        style={styles.addButton}
        onPress={() => router.push({ pathname: "/lista/novo-item", params: { listaId: lista.id } })}
      >
        <Text style={styles.addText}>+ Novo Item</Text>
      </TouchableOpacity>

      {/* 🟣 Botão para voltar pra Home */}
      <TouchableOpacity
        style={[styles.addButton, styles.backButton]}
        onPress={() => router.push("/")}
      >
        <Text style={styles.addText}>⬅ Voltar para Home</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#e7cfcf", padding: 10 },
  title: {
    fontSize: 22,
    fontWeight: "bold",
    textAlign: "center",
    marginVertical: 10,
    color: "#4a2e8e",
  },
  item: {
    backgroundColor: "#fff",
    padding: 15,
    borderRadius: 8,
    marginVertical: 5,
  },
  text: { fontSize: 16 },
  comprado: { textDecorationLine: "line-through", color: "#777" },
  actions: {
    flexDirection: "row",
    justifyContent: "flex-end",
    gap: 15,
    marginTop: 5,
  },
  edit: { color: "#7b3fcf", fontWeight: "bold" },
  delete: { color: "#c75b5b", fontWeight: "bold" },
  addButton: {
    backgroundColor: "#7b3fcf",
    padding: 15,
    borderRadius: 10,
    alignItems: "center",
    marginTop: 15,
  },
  backButton: {
    backgroundColor: "#999",
  },
  addText: { color: "#fff", fontSize: 16, fontWeight: "bold" },
});
