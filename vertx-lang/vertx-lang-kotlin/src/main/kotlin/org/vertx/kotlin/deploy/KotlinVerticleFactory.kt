package org.vertx.kotlin.deploy

import java.util.Arrays
import java.util.Collections
import org.jetbrains.jet.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.jet.lang.parsing.JetScriptDefinition
import org.jetbrains.jet.lang.resolve.AnalyzerScriptParameter
import org.jetbrains.jet.lang.resolve.name.Name
import org.jetbrains.jet.lang.types.ref.JetTypeName
import org.vertx.java.core.Vertx
import org.vertx.java.deploy.Container
import org.vertx.java.deploy.Verticle
import org.vertx.java.deploy.VerticleFactory
import org.vertx.java.deploy.impl.VerticleManager

public class KotlinVerticleFactory() : VerticleFactory {

    private var mgr : VerticleManager? = null

    public override fun init(manager: VerticleManager?) {
        this.mgr = mgr
    }

    public override fun createVerticle(main: String?, parentCL: ClassLoader?): Verticle? {
        val url = parentCL!!.getResource(main)
        val path: String? = url!!.getPath()
        val vertxParameter = AnalyzerScriptParameter(Name.identifier("vertx"), JetTypeName.fromJavaClass(javaClass<Vertx>()))
        val containerParameter = AnalyzerScriptParameter(Name.identifier("container"), JetTypeName.fromJavaClass(javaClass<Container>()))
        val verticleParameter = AnalyzerScriptParameter(Name.identifier("verticle"), JetTypeName.fromJavaClass(javaClass<KotlinScriptVerticle>()))
        val scriptParameters: List<AnalyzerScriptParameter>? = Arrays.asList(vertxParameter, containerParameter, verticleParameter)?.toList()
        val scriptDefinitions: List<JetScriptDefinition>? = Arrays.asList(JetScriptDefinition(".kts", Collections.emptyList<AnalyzerScriptParameter>()), JetScriptDefinition(".ktscript", Collections.emptyList<AnalyzerScriptParameter>()))?.toList()

        val clazz = KotlinToJVMBytecodeCompiler.compileScript(parentCL, path, scriptParameters, scriptDefinitions)!!
        val verticle = KotlinScriptVerticle(clazz)
        return verticle
    }

    public override fun reportException(t: Throwable?) {
        t!!.printStackTrace()
        mgr?.getLogger()!!.error("Exception in Kotlin verticle script", t)
    }
}
