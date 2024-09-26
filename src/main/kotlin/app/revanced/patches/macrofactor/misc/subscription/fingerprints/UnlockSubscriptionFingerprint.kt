package app.revanced.patches.macrofactor.misc.subscription.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

internal object CustomerInfoFactoryBuildCustomerInfoFingerprint : MethodFingerprint(
	strings = listOf("subscriber"),
	customFingerprint = { method, classDef ->
		classDef.endsWith("/CustomerInfoFactory;") && method.name == "buildCustomerInfo"
	}
)
