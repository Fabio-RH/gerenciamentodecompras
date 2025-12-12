    import { useState } from "react";
import { FlatList, StyleSheet, Text, TouchableOpacity, View } from "react-native";



 const ListaItem = ({ item, OnPress }) => {

      return (
        <View style={styles.cardItemRed}>
          <TouchableOpacity onPress={OnPress}>
            <Text style={styles.cardText}>{item.lista_nome}</Text>
          </TouchableOpacity>
        </View>
      );
    };

    const ListaList = ({ lista, OnPress }) => {
        return(
            <FlatList
                data={lista}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => <ListaItem OnPress={() => OnPress(item.lista_id, item.lista_nome)}  item={item} />}
            />
        )
    }



    const styles = StyleSheet.create({
      listContainer: {
        paddingTop: 20,
      },
      itemContainer: {
        backgroundColor: "#f9f9f9",
        padding: 15,
        marginVertical: 8,
        marginHorizontal: 16,
        borderRadius: 8,
        elevation: 2,
      },
      itemTouchable: {
        paddingVertical: 10,
      },
      itemTitle: {
        fontSize: 18,
        fontWeight: "bold",
      },
      itemContent: {
        fontSize: 16,
        marginTop: 10,
      },
    cardItemRed: {
        backgroundColor: 'rgba(239, 68, 68, 1)',
        borderRadius: 12,
        padding: 12,
        marginBottom: 10,
    },
    cardText: {
        color: '#FFF',
        fontSize: 14,
        fontWeight: '500',
    }
    });

export default ListaList;