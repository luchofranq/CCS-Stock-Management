import pandas as pd
import mysql.connector
import argparse

# Función para insertar datos en la base de datos
def insertar_datos(datos, host, user, password, database):
    try:
        conexion = mysql.connector.connect(
            host=host,
            user=user,
            password=password,
            database=database
        )
        cursor = conexion.cursor()
        # Sentencia SQL para insertar datos en una tabla
        sql = "INSERT INTO repuestoentrada(numeroDeParte, descripcion, cantidad, costo, costoValorizado, numeroPartida, numeroDespacho) VALUES (%s, %s, %s, %s, %s, %s, %s)"
        # Iterar sobre los datos y ejecutar la consulta para cada fila
        for fila in datos.itertuples():
            valores = (fila.COD_ARTICU, fila.DESCRIPCIO, fila.CANTIDAD, fila.COSTO, fila.COSTO_V, fila.N_PARTIDA, fila.N_DESPACH)
            cursor.execute(sql, valores)
        # Hacer commit para guardar los cambios en la base de datos
        conexion.commit()
        cursor.close()
        # Cerrar la conexión a la base de datos
        conexion.close()
        return True
    except mysql.connector.Error as error:
        return False, error

def main():
    parser = argparse.ArgumentParser(description="Insertar datos desde un archivo Excel a una base de datos MySQL")
    parser.add_argument("archivo_excel",type=str, help="Ruta al archivo Excel")
    parser.add_argument("--host", type=str, default='localhost', help="Host de la base de datos")
    parser.add_argument("--user", type=str, default='root', help="Usuario de la base de datos")
    parser.add_argument("--password", type=str, default='admin', help="Contraseña de la base de datos")
    parser.add_argument("--database", type=str, default='flow_database_login', help="Nombre de la base de datos")
    args = parser.parse_args()

    # Leer datos desde el archivo Excel
    try:
        datos_excel = pd.read_excel(args.archivo_excel)
    except FileNotFoundError:
        print("El archivo no existe")
        return False
    finally:
        print("Lectura de datos exitosa")
    

    # Insertar datos en la base de datos
    try:
        resultado = insertar_datos(datos_excel, args.host, args.user, args.password, args.database)
        if resultado == True:
            print("Datos insertados correctamente")
        else:
            print("Error al insertar datos: ", resultado[1])
    except Exception as e:
        print("Error al insertar datos: ", e)
        return False
        
if __name__ == "__main__":
    main()