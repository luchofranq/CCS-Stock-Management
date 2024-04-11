import pandas as pd
import mysql.connector
import argparse

def agregar_precios_locales(datos_nuevos, host, user, password, database):
    try:
        conexion = mysql.connector.connect(
            host=host,
            user=user,
            password=password,
            database=database
        )
        cursor = conexion.cursor()

        for fila in datos_nuevos.itertuples():
            # Verificar si el número de parte ya existe en la tabla repuestoentrada
            sql_select = "SELECT COUNT(*) FROM repuestoentrada WHERE numeroDeParte = %s"
            cursor.execute(sql_select, (fila.COD_ARTICU,))
            resultado = cursor.fetchone()

            if resultado[0] > 0:
                # Si existe, actualizar el precioLocal
                precio_local_nuevo = fila.P_LOCAL
                precio_exterior_nuevo = fila.P_EXTERIOR
                sql_update = "UPDATE repuesto SET precioLocal = %s , precioExterior = %s WHERE numeroDeParte = %s"
                cursor.execute(sql_update, (precio_local_nuevo,precio_exterior_nuevo, fila.COD_ARTICU))
                conexion.commit()
                


                print(f"PrecioLocal actualizado para el número de parte {fila.COD_ARTICU}")
            else:
                print(f"El número de parte {fila.COD_ARTICU} no existe en la base de datos")

        cursor.close()
        conexion.close()
        return True
    except mysql.connector.Error as error:
        return False, error

def main():
    parser = argparse.ArgumentParser(description="Agregar precios desde un archivo Excel a una base de datos MySQL")
    parser.add_argument("archivo_excel", type=str, help="Ruta al archivo Excel")
    parser.add_argument("--host", type=str, default='localhost', help="Host de la base de datos")
    parser.add_argument("--user", type=str, default='root', help="Usuario de la base de datos")
    parser.add_argument("--password", type=str, default='admin', help="Contraseña de la base de datos")
    parser.add_argument("--database", type=str, default='flow_database_login', help="Nombre de la base de datos")
    args = parser.parse_args()

    try:
        datos_excel = pd.read_excel(args.archivo_excel)
    except FileNotFoundError:
        print("El archivo no existe")
        return False
    finally:
        print("Lectura de datos exitosa")

    try:
        resultado = agregar_precios_locales(datos_excel, args.host, args.user, args.password, args.database)
        if resultado == True:
            print("Precios agregados correctamente")
        else:
            print("Error al agregar precios: ", resultado[1])
    except Exception as e:
        print("Error al agregar precios: ", e)
        return False
        
if __name__ == "__main__":
    main()