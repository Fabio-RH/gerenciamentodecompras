// app/index.tsx

import { AntDesign } from "@expo/vector-icons";
import { useRouter } from "expo-router";
import { FlatList, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useListas } from "../context/ListasContext";

export default function ListaPage() {
  const router = useRouter();
  const { listas } = useListas() as { listas: { id: number | string; nome: string }[] };
  return (
    <View style={styles.container}>
      <Text style={styles.titulo}>Listas</Text>

      {listas.length === 0 ? (
        <Text style={styles.vazio}>Nenhuma lista criada ainda.</Text>
      ) : (
        <FlatList
          data={listas}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <TouchableOpacity
              style={styles.item}
              onPress={() => router.push(`/lista/${item.id}`)}
            >
              <Text style={styles.texto}>{item.nome}</Text>
            </TouchableOpacity>
          )}
        />
      )}

      <TouchableOpacity
        style={styles.botao}
        // 🟢 MUDANÇA AQUI: Navegando para a nova subpasta `nova/index.tsx`
        onPress={() => router.push("/lista/nova")}
      >
        <AntDesign name="plus-circle" size={54} color="#7b3fcf" />
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 20, backgroundColor: "#fff" },
  titulo: { fontSize: 26, fontWeight: "bold", color: "#7b3fcf", marginBottom: 20 },
  item: {
    padding: 15,
    borderBottomWidth: 1,
    borderBottomColor: "#ccc",
  },
  texto: { fontSize: 18 },
  vazio: { fontSize: 16, color: "#777", textAlign: "center", marginTop: 30 },
  botao: {
    position: "absolute",
    bottom: 25,
    right: 25,
  },
});