# TaskProcessingApp
App contains two servers: queue and executor.
Queue server interacts with client via http requests.
Executor server continuously requests the new tasks from queue server and performs them.
Everything happens asynchronously.

**Used technologies:**
* Java
* Spring Boot
* Spring Data JPA / Hibernate
* MySQL RDBMS

**Task description:**
<p align="center"><img src="img/task-desk-1.jpg"></p>

**Services relationship diagram:**
<p align="center"><img src="img/task-desk-2.jpg"></p>

**Screenshots:**
<p align="center"><img src="img/img1.png"></p>
<p align="center"><img src="img/img2.png"></p>
<p align="center"><img src="img/img3.png"></p>
