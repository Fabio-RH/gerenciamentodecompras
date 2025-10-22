import { useLocalSearchParams, useRouter } from "expo-router";
import { Alert, FlatList, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useListas } from "../../context/ListasContext";

export default function ListaDetalhes() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const router = useRouter();
  const ctx = useListas() as any;
  const { listas, toggleItem } = ctx;

  const lista = listas.find((l: any) => l.id === id);
  if (!lista) return <Text>Lista não encontrada.</Text>;

  return (
    <View style={styles.container}>
      <FlatList
        data={lista.itens}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={styles.item}
            onPress={() => toggleItem(lista.id, item.id)}
          >
            <Text style={[styles.text, item.comprado && styles.comprado]}>
              {item.nome}
            </Text>
            <View style={styles.actions}>
              <TouchableOpacity
                onPress={() =>
                  router.push({
                    pathname: "/lista/novo-item",
                    params: { listaId: lista.id, itemId: item.id, nome: item.nome },
                  } as any)
                }
              >
                <Text style={styles.edit}>Editar</Text>
              </TouchableOpacity>
              <TouchableOpacity
                onPress={() =>
                  Alert.alert("Excluir item", "Deseja excluir este item?", [
                    { text: "Cancelar", style: "cancel" },
                    {
                      text: "Excluir",
                      onPress: () => ctx.excluirItem(lista.id, item.id),
                    },
                  ])
                }
              >
                <Text style={styles.delete}>Excluir</Text>
              </TouchableOpacity>
            </View>
          </TouchableOpacity>
        )}
      />

      <TouchableOpacity
        style={styles.addButton}
        onPress={() =>
          router.push({ pathname: "/lista/novo-item", params: { listaId: lista.id } } as any)
        }
      >
        <Text style={styles.addText}>+ Novo Item</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#e7cfcf", padding: 10 },
  item: { backgroundColor: "#fff", padding: 15, borderRadius: 8, marginVertical: 5 },
  text: { fontSize: 16 },
  comprado: { textDecorationLine: "line-through", color: "#777" },
  actions: { flexDirection: "row", justifyContent: "flex-end", gap: 15, marginTop: 5 },
  edit: { color: "#7b3fcf", fontWeight: "bold" },
  delete: { color: "#c75b5b", fontWeight: "bold" },
  addButton: {
    backgroundColor: "#7b3fcf",
    padding: 15,
    borderRadius: 10,
    alignItems: "center",
    marginTop: 15,
  },
  addText: { color: "#fff", fontSize: 16, fontWeight: "bold" },
});
