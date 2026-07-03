// Top-level build file
plugins {
    // এই প্লাগইনগুলো ব্যবহার করা হচ্ছে, কিন্তু এখানে সরাসরি রান করা হচ্ছে না (apply false)
    // এগুলো ভার্সন ক্যাটালগ (libs.versions.toml) থেকে আসছে
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.google.services) apply false
}
