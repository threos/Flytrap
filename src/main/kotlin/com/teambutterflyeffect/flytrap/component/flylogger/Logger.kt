package com.teambutterflyeffect.flytrap.component.flylogger

import com.teambutterflyeffect.flytrap.component.flylogger.storage.LogStorage
import java.util.*

fun log(tag: String, message: String, level: LogLevel = LogLevel.INFO, internal: Boolean = false) {
    val item = LogItem(tag, message, level)
    println(item)
    if(!internal) LogStorage.append(item)
}

enum class LogLevel {
    VERBOSE,
    INFO,
    DEBUG,
    WARNING,
    ERROR,
    CRITICAL,
}

data class LogItem(
    val tag: String,
    val message: String,
    val level: LogLevel,
    val date: Date = Date(),
) {
    override fun toString() = "(${date})\t [${level.name}] ${tag}:\t ${message.replace("\n", "\n${"\t".repeat(11)}")}"
}

const val logo = """                                                                                                    
                                                                                                    
                                                                                                    
                                            ,.                                                      
                                 &/         @           @                                           
                       ,@         @         @          @                                            
                        @#        @        @@         @            ,                                
               %@        @.       @        @         @           @                                  
                 @       #@&     ,@@      @@        @          @%                                   
      &     @     @@     @@@@@@*@@@@@&   @@@.     #@         &@             @                       
        @    .@    @@@@@@@@@@@@@@@@@@@@@@@@@@@@#@@@@       %@            @&                         
          @    @@@@@@@@@@@@@@@@&&&@@@@@@@@@@@@@@@@@@@    @@@          @@                            
           &@@#@@@@@@@@   .@     .@         #@@@@@@@@@@@@@@        @@                               
           @@@@@@@@@@@@@@ @@@    %%       ((     ,@@@@@@@@@@   *@@%              &@                 
          @@@@@@@@@@@@@@@@@@@@   @%      ,@          (@@@@@@@@@@@           /@@                     
         @@@@@@@@@@@@@@@@@@@@@@@@@@     *@              ,@@@@@@@@.      @@/                         
        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@(.@@          @       @@@@@@@@@@@@                             
       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@       *@           @@@@@@@@#              @@@(            
       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@   @@               @@@@@@@@@//%@@@@*                    
        %@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                  @@@@@@@@@                          
           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@       @@#        @@@@@@@                           
             @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@              @@@@@@@@@@@@@@@@@@@@@@@#,        
               @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                @@@@@@@                         
                 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%              @@@@@@                         
                   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@             @@@@@@@@@*                     
                     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            @@@@@@              /@@@       
                       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@       @@@@@@                         
                         (@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@          @@@@@@@@%                      
                           (@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@       @@@@@%       &@@@               
                             @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @@@ @@@@@@.                          
                               @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@                        
                                 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@        @@                    
                                  %@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                              
                                    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@     .@@                           
                                      @@@@@@@@@@@@@@@@@@@@@@@,    @       @@                        
                                                              @(     @&                             
                                                                 @                                  
                                                                                                    
                                                                                                    
                                                                                                    
"""