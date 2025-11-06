import { Tabs } from "expo-router";

export default function TabsLayout() {
  return (
    <Tabs>
      <Tabs.Screen
        name="lista"
        options={{ title: "Listas" }}
      />
      <Tabs.Screen
        name="cadastro"
        options={{ title: "Cadastro" }}
      />
    </Tabs>
  );
}
