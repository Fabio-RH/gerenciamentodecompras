import { Stack } from "expo-router";
import { ListasProvider } from "../context/ListasContext";

export default function Layout() {
  return (
    <ListasProvider>
      <Stack
        screenOptions={{
          headerStyle: { backgroundColor: "#7b3fcf" },
          headerTintColor: "#fff",
          headerTitleAlign: "center",
          headerShown: false,
        }}
      />
    </ListasProvider>
  );
}
