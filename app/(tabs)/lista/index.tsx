import { View, Text, StyleSheet } from "react-native";

export default function ListaScreen() {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Tela de Lista</Text>
      <Text>Aqui vai o conteúdo principal da aba Lista.</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#fff",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 10,
  },
});
