# aplicaciones-moviles-

# Aplicación Móvil de Gestión Documental - Área Salud CMQ

**Asignatura:** Desarrollo de Aplicaciones Móviles (DSY1105)  
**Institución:** Duoc UC - Sede Quilpué  
**Organización Beneficiaria:** Corporación Municipal de Quilpué (CMQ) - Área Salud  
**Docente:** Bastián Lisperguer  

---

## 👥 Integrantes del Equipo
* **Integrante 1:** Joaquin allendes
* **Integrante 2:** 

---

## 📌 Problemática
El Área de Salud de la Corporación Municipal de Quilpué (CMQ) administra múltiples establecimientos de Atención Primaria de Salud (APS) como CESFAM y CECOSF. La gestión actual de los expedientes personales de los funcionarios presenta dificultades en la centralización, tiempos de consulta, estandarización de cargas y auditoría de accesos a la documentación administrativa y de capacitación.

---

## 🎯 Objetivo General
Desarrollar un Producto Mínimo Viable (MVP) de una aplicación móvil nativa para Android que permita a los funcionarios y jefaturas de la red APS de Quilpué digitalizar, consultar, solicitar permisos y auditar expedientes documentales de manera centralizada, conectándose con Microsoft SharePoint y garantizando el control de acceso por roles (RBAC) y la trazabilidad de operaciones.

---

## 🛠️ Tecnologías y Herramientas Consideradas
* **Entorno de Desarrollo:** Android Studio
* **Lenguaje de Programación:** Kotlin
* **Framework de Interfaz de Usuario:** Jetpack Compose
* **Patrón de Arquitectura:** MVVM (Model-View-ViewModel)
* **Repositorio y Almacenamiento Central:** Microsoft SharePoint
* **Control de Versiones:** Git / GitHub

---

## 📁 Estrategia de Arquitectura y Carpetas
```text
cl.duoc.cmqsalud/
├── data/          # Repositorios, servicios de red e integración SharePoint
├── model/         # Modelos de datos (Usuario, Documento, Solicitud, Log)
├── ui/            # Vistas Compose y ViewModels
│   ├── login/
│   ├── dashboard/
│   ├── carga/
│   └── visor/
└── navigation/    # Control de rutas y flujo de pantallas
