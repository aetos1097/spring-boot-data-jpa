# Proyecto Data JPA

En esta aplicacion se mostrara como es la funcionalidad de JPA(JavaPersistenceAPi),el cual es
manejado por hibernate(ORM).

## Paquetes
* controller:
  * Proporciona las clases del tipo controllers anotadas por @Controller, estas clases manejarann las
   solicitudes http y las dirigira por los metodos get,post,put,delete(CRUD)
* models:
  * Dentro de los modelos iran los servicios con sus interfaces, esto quiere decir que dentro de
    este paquete ira la logica de la trnasmision de datos llamadas por los controladores, por lo general
   se anota la clase con @Service
* entity:
  * Dentro de este paquete ira las clases que seran mapeadas a las tablas en la base de datos
    es decir todos los atributos de las clases corresponderan a las columnas en cada tabla de nuestra DB

## Resources/Templates
En esta ruta iran las plantillas es decir la vista de la aplicacion por medio de archivos html
ya que usamos la depedencia de Thymleaf es posible pasar los datos a la vista con la etiqueta `th:(nombre etiqueta)`

## Clases e Interfaces
* controller/ClienteCController: Esta clase manejara los metodos del Crud(Listar, Crear,Modificar,Guardar,Eliminar).Cada
uno mapeado a la ruta url de cada metodo ejemplo listar => 
~~~
@RequestMapping(value = "/listar",method= RequestMethod.GET)
  public String listar(Model model)  
~~~
* models/dao/IClienteDao: intefaz que traera los metodos del Crud para implementar
*models/dao/ClienteDaoImpl: esta clase implementara la interza ICliente Dao y dentro
se dara toda la logica de los controladores(Ojo! tener en cuenta que le fragemento no lleva las anotaciones, algo que se
debe ver en la clase **ClienteDaoImpl**)
~~~
public List findAll() {
        return em.createQuery("from Cliente").getResultList();// aparece error pero si funciona
    }
~~~


