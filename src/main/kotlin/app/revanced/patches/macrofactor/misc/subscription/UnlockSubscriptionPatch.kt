package app.revanced.patches.macrofactor.misc.subscription

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patches.macrofactor.misc.subscription.fingerprints.CustomerInfoFactoryBuildCustomerInfoFingerprint

@Patch(
    name = "Unlock subscription",
    description = "Unlocks the app.",
    compatiblePackages = [CompatiblePackage("com.sbs.diet")]
)
@Suppress("unused")

object UnlockSubscriptionPatch : BytecodePatch(
    setOf(CustomerInfoFactoryBuildCustomerInfoFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        val method = CustomerInfoFactoryBuildCustomerInfoFingerprint.result!!.mutableMethod
        method.addInstructionsWithLabels(
            0,
            """
                    const-string v3, "subscriber"
                    move-object/from16 v0, p1
                    invoke-virtual {v0, v3}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;
                    move-result-object v0
                    
                    const-string v4, "original_app_user_id"
                    invoke-virtual {v0, v4}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;
                    move-result-object v2
                
                    const-string v1, "{\"request_date_ms\":1689736676913,\"request_date\":\"2023-07-19T03:17:56Z\",\"subscriber\":{\"non_subscriptions\":{},\"first_seen\":\"2023-07-19T03:17:14Z\",\"original_application_version\":\"246\",\"other_purchases\":{},\"management_url\":\"https://apps.apple.com/account/subscriptions\",\"subscriptions\":{\"com.sbs.diet.1y0599.2w0\":{\"original_purchase_date\":\"2023-07-19T03:17:53Z\",\"expires_date\":\"2099-08-02T03:17:52Z\",\"is_sandbox\":false,\"refunded_at\":null,\"unsubscribe_detected_at\":null,\"grace_period_expires_date\":null,\"period_type\":\"normal\",\"purchase_date\":\"2023-07-19T03:17:52Z\",\"billing_issues_detected_at\":null,\"ownership_type\":\"PURCHASED\",\"store\":\"app_store\",\"auto_resume_date\":null}},\"entitlements\":{\"subscription\":{\"grace_period_expires_date\":null,\"purchase_date\":\"2023-07-19T03:17:52Z\",\"product_identifier\":\"com.sbs.diet.1y0599.2w0\",\"expires_date\":\"2099-08-02T03:17:52Z\"}},\"original_purchase_date\":\"2023-07-18T21:29:29Z\",\"original_app_user_id\":\"id\",\"last_seen\":\"2023-07-19T03:17:14Z\"}}"
                    new-instance v0, Lorg/json/JSONObject;
                    invoke-direct {v0, v1}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V
                    
                    invoke-virtual {v0, v3}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;
                    move-result-object v1
                    
                    invoke-virtual {v1, v4, v2}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
                    move-object/from16 p1, v0
            """,
        )
    }
}
