import React, { createContext, useContext, useState, ReactNode } from "react";

export type Item = {
  id: string;
  nome: string;
  comprado: boolean;
};

export type Lista = {
  id: string;
  nome: string;
  itens: Item[];
};

type ListasContextType = {
  listas: Lista[];
  adicionarLista: (nome: string) => void;
  editarLista: (id: string, nome: string) => void;
  removerLista: (id: string) => void;
  getLista: (id: string) => Lista | undefined;
  adicionarItem: (listaId: string, nome: string) => void;
  excluirItem: (listaId: string, itemId: string) => void;
  toggleItem: (listaId: string, itemId: string) => void;
};

const ListasContext = createContext<ListasContextType | undefined>(undefined);

export const ListasProvider = ({ children }: { children: ReactNode }) => {
  const [listas, setListas] = useState<Lista[]>([]);

  const adicionarLista = (nome: string) => {
    const novaLista: Lista = { id: Date.now().toString(), nome, itens: [] };
    setListas((prev) => [...prev, novaLista]);
  };

  const editarLista = (id: string, nome: string) => {
    setListas((prev) =>
      prev.map((lista) => (lista.id === id ? { ...lista, nome } : lista))
    );
  };

  const removerLista = (id: string) => {
    setListas((prev) => prev.filter((lista) => lista.id !== id));
  };

  const getLista = (id: string) => listas.find((lista) => lista.id === id);

  const adicionarItem = (listaId: string, nome: string) => {
    setListas((prev) =>
      prev.map((lista) =>
        lista.id === listaId
          ? {
              ...lista,
              itens: [...lista.itens, { id: Date.now().toString(), nome, comprado: false }],
            }
          : lista
      )
    );
  };

  const excluirItem = (listaId: string, itemId: string) => {
    setListas((prev) =>
      prev.map((lista) =>
        lista.id === listaId
          ? { ...lista, itens: lista.itens.filter((item) => item.id !== itemId) }
          : lista
      )
    );
  };

  const toggleItem = (listaId: string, itemId: string) => {
    setListas((prev) =>
      prev.map((lista) =>
        lista.id === listaId
          ? {
              ...lista,
              itens: lista.itens.map((item) =>
                item.id === itemId ? { ...item, comprado: !item.comprado } : item
              ),
            }
          : lista
      )
    );
  };

  return (
    <ListasContext.Provider
      value={{
        listas,
        adicionarLista,
        editarLista,
        removerLista,
        getLista,
        adicionarItem,
        excluirItem,
        toggleItem,
      }}
    >
      {children}
    </ListasContext.Provider>
  );
};

export const useListas = () => {
  const context = useContext(ListasContext);
  if (!context) {
    throw new Error("useListas deve ser usado dentro de um ListasProvider");
  }
  return context;
};

export default ListasContext;
