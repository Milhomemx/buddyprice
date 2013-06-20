 $(document).ready(function(){
    separar('.elementos', '.elemento', 0);
});

function separar(contenedor, elementos, correccion){
         
        // Obtenemos el ancho total disponible para distribuir el espacio
        var total = $(contenedor).width();
 
        // Contador para sumar los anchos de nuestros elementos
        var parcial = 0;
 
        // Calculamos el espacio utilizado por los elementos
        $(elementos).each(function(){
            parcial += $(this).width();
            parcial += parseInt($(this).css('padding-left'));
            parcial += parseInt($(this).css('padding-right'));
        });
 
        // Si los elementos ocupan más espacio que el disponible no hay nada que hacer
        if(parcial > total) return false;
 
        // Calculamos el espaciado a otorgar a cada elemento (excepto el 1 y último)
        var espacio = Math.ceil((total - parcial) / ($(elementos).length));
 
        // Asignamos el espacio obtenido
        $(elementos).each(function(index){
            if((index + 1) != $(elementos).length){
                $(this).css('margin-right', espacio + 'px');
                 
                // parcial ahora almacena todo el espacio ocupado
                parcial += espacio;
            }
        });
 
        // Calculamos lo que falte por centrar
        // (en la mayoría de los casos se pierde espacio al redondear)
        var centrar = Math.floor((total - parcial) * 0.5);
 
        // Centramos y aplicamos la corrección
        $(elementos + ':eq(0)').css('margin-left', (centrar - correccion) + 'px');
 
        return true;
   }