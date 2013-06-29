
zk.afterMount(function() {
            jq("$fldNome").Watermark("Seu nome","gray");
            jq("$fldUltNome").Watermark("Sobrenome","gray"); 
            jq("$fldEmail").Watermark("Seu e-mail","gray"); 
            jq("$fldNasc").Watermark("Data de Nascimento","gray"); 
            jq("$fldEmail2").Watermark("Digite seu e-mail novamente","gray"); 
            jq("$fldtel").Watermark("Digite seu telefone celular","gray");
            jq("$flddataNasc").Watermark("Digite sua data de nascimento","gray");
            jq("$fldPass").Watermark("                      ","gray");
            jq("$fldPass2").Watermark("                      ","gray");
                         
        });