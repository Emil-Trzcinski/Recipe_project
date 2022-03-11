package pl.trzcinski.emil.recipeproject.domain;

import com.fasterxml.jackson.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nutrition",
    "sections",
    "buzz_id",
    "created_at",
    "prep_time_minutes",
    "original_video_url",
    "beauty_url",
    "inspired_by_url",
    "seo_title",
    "tips_and_ratings_enabled",
    "total_time_minutes",
    "video_url",
    "compilations",
    "facebook_posts",
    "id",
    "renditions",
    "language",
    "slug",
    "video_id",
    "instructions",
    "topics",
    "is_shoppable",
    "draft_status",
    "updated_at",
    "video_ad_content",
    "country",
    "is_one_top",
    "keywords",
    "promotion",
    "servings_noun_plural",
    "servings_noun_singular",
    "show_id",
    "thumbnail_alt_text",
    "aspect_ratio",
    "total_time_tier",
    "user_ratings",
    "credits",
    "canonical_id",
    "thumbnail_url",
    "brand_id",
    "description",
    "num_servings",
    "yields",
    "nutrition_visibility",
    "show",
    "approved_at",
    "name",
    "brand",
    "tags",
    "cook_time_minutes"
})

@Generated("jsonschema2pojo")
@Component
public class Recipe {

    @JsonProperty("nutrition")
    private Nutrition nutrition;
    @JsonProperty("sections")
    private List<Section> sections = null;
    @JsonProperty("buzz_id")
    private Object buzzId;
    @JsonProperty("created_at")
    private Integer createdAt;
    @JsonProperty("prep_time_minutes")
    private Object prepTimeMinutes;
    @JsonProperty("original_video_url")
    private Object originalVideoUrl;
    @JsonProperty("beauty_url")
    private Object beautyUrl;
    @JsonProperty("inspired_by_url")
    private Object inspiredByUrl;
    @JsonProperty("seo_title")
    private String seoTitle;
    @JsonProperty("tips_and_ratings_enabled")
    private Boolean tipsAndRatingsEnabled;
    @JsonProperty("total_time_minutes")
    private Object totalTimeMinutes;
    @JsonProperty("video_url")
    private Object videoUrl;
    @JsonProperty("compilations")
    private List<Object> compilations = null;
    @JsonProperty("facebook_posts")
    private List<Object> facebookPosts = null;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("renditions")
    private List<Object> renditions = null;
    @JsonProperty("language")
    private String language;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("video_id")
    private Object videoId;
    @JsonProperty("instructions")
    private List<Instruction> instructions = null;
    @JsonProperty("topics")
    private List<Topic> topics = null;
    @JsonProperty("is_shoppable")
    private Boolean isShoppable;
    @JsonProperty("draft_status")
    private String draftStatus;
    @JsonProperty("updated_at")
    private Integer updatedAt;
    @JsonProperty("video_ad_content")
    private Object videoAdContent;
    @JsonProperty("country")
    private String country;
    @JsonProperty("is_one_top")
    private Boolean isOneTop;
    @JsonProperty("keywords")
    private String keywords;
    @JsonProperty("promotion")
    private String promotion;
    @JsonProperty("servings_noun_plural")
    private String servingsNounPlural;
    @JsonProperty("servings_noun_singular")
    private String servingsNounSingular;
    @JsonProperty("show_id")
    private Integer showId;
    @JsonProperty("thumbnail_alt_text")
    private String thumbnailAltText;
    @JsonProperty("aspect_ratio")
    private String aspectRatio;
    @JsonProperty("total_time_tier")
    private TotalTimeTier totalTimeTier;
    @JsonProperty("user_ratings")
    private UserRatings userRatings;
    @JsonProperty("credits")
    private List<Credit> credits = null;
    @JsonProperty("canonical_id")
    private String canonicalId;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    @JsonProperty("brand_id")
    private Object brandId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("num_servings")
    private Integer numServings;
    @JsonProperty("yields")
    private String yields;
    @JsonProperty("nutrition_visibility")
    private String nutritionVisibility;
    @JsonProperty("show")
    private Show show;
    @JsonProperty("approved_at")
    private Integer approvedAt;
    @JsonProperty("name")
    private String name;
    @JsonProperty("brand")
    private Object brand;
    @JsonProperty("tags")
    private List<Tag> tags = null;
    @JsonProperty("cook_time_minutes")
    private Object cookTimeMinutes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("nutrition")
    public Nutrition getNutrition() {
        return nutrition;
    }

    @JsonProperty("nutrition")
    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public Recipe withNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
        return this;
    }

    @JsonProperty("sections")
    public List<Section> getSections() {
        return sections;
    }

    @JsonProperty("sections")
    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Recipe withSections(List<Section> sections) {
        this.sections = sections;
        return this;
    }

    @JsonProperty("buzz_id")
    public Object getBuzzId() {
        return buzzId;
    }

    @JsonProperty("buzz_id")
    public void setBuzzId(Object buzzId) {
        this.buzzId = buzzId;
    }

    public Recipe withBuzzId(Object buzzId) {
        this.buzzId = buzzId;
        return this;
    }

    @JsonProperty("created_at")
    public Integer getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Recipe withCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @JsonProperty("prep_time_minutes")
    public Object getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    @JsonProperty("prep_time_minutes")
    public void setPrepTimeMinutes(Object prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public Recipe withPrepTimeMinutes(Object prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
        return this;
    }

    @JsonProperty("original_video_url")
    public Object getOriginalVideoUrl() {
        return originalVideoUrl;
    }

    @JsonProperty("original_video_url")
    public void setOriginalVideoUrl(Object originalVideoUrl) {
        this.originalVideoUrl = originalVideoUrl;
    }

    public Recipe withOriginalVideoUrl(Object originalVideoUrl) {
        this.originalVideoUrl = originalVideoUrl;
        return this;
    }

    @JsonProperty("beauty_url")
    public Object getBeautyUrl() {
        return beautyUrl;
    }

    @JsonProperty("beauty_url")
    public void setBeautyUrl(Object beautyUrl) {
        this.beautyUrl = beautyUrl;
    }

    public Recipe withBeautyUrl(Object beautyUrl) {
        this.beautyUrl = beautyUrl;
        return this;
    }

    @JsonProperty("inspired_by_url")
    public Object getInspiredByUrl() {
        return inspiredByUrl;
    }

    @JsonProperty("inspired_by_url")
    public void setInspiredByUrl(Object inspiredByUrl) {
        this.inspiredByUrl = inspiredByUrl;
    }

    public Recipe withInspiredByUrl(Object inspiredByUrl) {
        this.inspiredByUrl = inspiredByUrl;
        return this;
    }

    @JsonProperty("seo_title")
    public String getSeoTitle() {
        return seoTitle;
    }

    @JsonProperty("seo_title")
    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public Recipe withSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
        return this;
    }

    @JsonProperty("tips_and_ratings_enabled")
    public Boolean getTipsAndRatingsEnabled() {
        return tipsAndRatingsEnabled;
    }

    @JsonProperty("tips_and_ratings_enabled")
    public void setTipsAndRatingsEnabled(Boolean tipsAndRatingsEnabled) {
        this.tipsAndRatingsEnabled = tipsAndRatingsEnabled;
    }

    public Recipe withTipsAndRatingsEnabled(Boolean tipsAndRatingsEnabled) {
        this.tipsAndRatingsEnabled = tipsAndRatingsEnabled;
        return this;
    }

    @JsonProperty("total_time_minutes")
    public Object getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    @JsonProperty("total_time_minutes")
    public void setTotalTimeMinutes(Object totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
    }

    public Recipe withTotalTimeMinutes(Object totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
        return this;
    }

    @JsonProperty("video_url")
    public Object getVideoUrl() {
        return videoUrl;
    }

    @JsonProperty("video_url")
    public void setVideoUrl(Object videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Recipe withVideoUrl(Object videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    @JsonProperty("compilations")
    public List<Object> getCompilations() {
        return compilations;
    }

    @JsonProperty("compilations")
    public void setCompilations(List<Object> compilations) {
        this.compilations = compilations;
    }

    public Recipe withCompilations(List<Object> compilations) {
        this.compilations = compilations;
        return this;
    }

    @JsonProperty("facebook_posts")
    public List<Object> getFacebookPosts() {
        return facebookPosts;
    }

    @JsonProperty("facebook_posts")
    public void setFacebookPosts(List<Object> facebookPosts) {
        this.facebookPosts = facebookPosts;
    }

    public Recipe withFacebookPosts(List<Object> facebookPosts) {
        this.facebookPosts = facebookPosts;
        return this;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Recipe withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("renditions")
    public List<Object> getRenditions() {
        return renditions;
    }

    @JsonProperty("renditions")
    public void setRenditions(List<Object> renditions) {
        this.renditions = renditions;
    }

    public Recipe withRenditions(List<Object> renditions) {
        this.renditions = renditions;
        return this;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    public Recipe withLanguage(String language) {
        this.language = language;
        return this;
    }

    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Recipe withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    @JsonProperty("video_id")
    public Object getVideoId() {
        return videoId;
    }

    @JsonProperty("video_id")
    public void setVideoId(Object videoId) {
        this.videoId = videoId;
    }

    public Recipe withVideoId(Object videoId) {
        this.videoId = videoId;
        return this;
    }

    @JsonProperty("instructions")
    public List<Instruction> getInstructions() {
        return instructions;
    }

    @JsonProperty("instructions")
    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public Recipe withInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
        return this;
    }

    @JsonProperty("topics")
    public List<Topic> getTopics() {
        return topics;
    }

    @JsonProperty("topics")
    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public Recipe withTopics(List<Topic> topics) {
        this.topics = topics;
        return this;
    }

    @JsonProperty("is_shoppable")
    public Boolean getIsShoppable() {
        return isShoppable;
    }

    @JsonProperty("is_shoppable")
    public void setIsShoppable(Boolean isShoppable) {
        this.isShoppable = isShoppable;
    }

    public Recipe withIsShoppable(Boolean isShoppable) {
        this.isShoppable = isShoppable;
        return this;
    }

    @JsonProperty("draft_status")
    public String getDraftStatus() {
        return draftStatus;
    }

    @JsonProperty("draft_status")
    public void setDraftStatus(String draftStatus) {
        this.draftStatus = draftStatus;
    }

    public Recipe withDraftStatus(String draftStatus) {
        this.draftStatus = draftStatus;
        return this;
    }

    @JsonProperty("updated_at")
    public Integer getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Recipe withUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @JsonProperty("video_ad_content")
    public Object getVideoAdContent() {
        return videoAdContent;
    }

    @JsonProperty("video_ad_content")
    public void setVideoAdContent(Object videoAdContent) {
        this.videoAdContent = videoAdContent;
    }

    public Recipe withVideoAdContent(Object videoAdContent) {
        this.videoAdContent = videoAdContent;
        return this;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    public Recipe withCountry(String country) {
        this.country = country;
        return this;
    }

    @JsonProperty("is_one_top")
    public Boolean getIsOneTop() {
        return isOneTop;
    }

    @JsonProperty("is_one_top")
    public void setIsOneTop(Boolean isOneTop) {
        this.isOneTop = isOneTop;
    }

    public Recipe withIsOneTop(Boolean isOneTop) {
        this.isOneTop = isOneTop;
        return this;
    }

    @JsonProperty("keywords")
    public String getKeywords() {
        return keywords;
    }

    @JsonProperty("keywords")
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Recipe withKeywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    @JsonProperty("promotion")
    public String getPromotion() {
        return promotion;
    }

    @JsonProperty("promotion")
    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public Recipe withPromotion(String promotion) {
        this.promotion = promotion;
        return this;
    }

    @JsonProperty("servings_noun_plural")
    public String getServingsNounPlural() {
        return servingsNounPlural;
    }

    @JsonProperty("servings_noun_plural")
    public void setServingsNounPlural(String servingsNounPlural) {
        this.servingsNounPlural = servingsNounPlural;
    }

    public Recipe withServingsNounPlural(String servingsNounPlural) {
        this.servingsNounPlural = servingsNounPlural;
        return this;
    }

    @JsonProperty("servings_noun_singular")
    public String getServingsNounSingular() {
        return servingsNounSingular;
    }

    @JsonProperty("servings_noun_singular")
    public void setServingsNounSingular(String servingsNounSingular) {
        this.servingsNounSingular = servingsNounSingular;
    }

    public Recipe withServingsNounSingular(String servingsNounSingular) {
        this.servingsNounSingular = servingsNounSingular;
        return this;
    }

    @JsonProperty("show_id")
    public Integer getShowId() {
        return showId;
    }

    @JsonProperty("show_id")
    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public Recipe withShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    @JsonProperty("thumbnail_alt_text")
    public String getThumbnailAltText() {
        return thumbnailAltText;
    }

    @JsonProperty("thumbnail_alt_text")
    public void setThumbnailAltText(String thumbnailAltText) {
        this.thumbnailAltText = thumbnailAltText;
    }

    public Recipe withThumbnailAltText(String thumbnailAltText) {
        this.thumbnailAltText = thumbnailAltText;
        return this;
    }

    @JsonProperty("aspect_ratio")
    public String getAspectRatio() {
        return aspectRatio;
    }

    @JsonProperty("aspect_ratio")
    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Recipe withAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
        return this;
    }

    @JsonProperty("total_time_tier")
    public TotalTimeTier getTotalTimeTier() {
        return totalTimeTier;
    }

    @JsonProperty("total_time_tier")
    public void setTotalTimeTier(TotalTimeTier totalTimeTier) {
        this.totalTimeTier = totalTimeTier;
    }

    public Recipe withTotalTimeTier(TotalTimeTier totalTimeTier) {
        this.totalTimeTier = totalTimeTier;
        return this;
    }

    @JsonProperty("user_ratings")
    public UserRatings getUserRatings() {
        return userRatings;
    }

    @JsonProperty("user_ratings")
    public void setUserRatings(UserRatings userRatings) {
        this.userRatings = userRatings;
    }

    public Recipe withUserRatings(UserRatings userRatings) {
        this.userRatings = userRatings;
        return this;
    }

    @JsonProperty("credits")
    public List<Credit> getCredits() {
        return credits;
    }

    @JsonProperty("credits")
    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public Recipe withCredits(List<Credit> credits) {
        this.credits = credits;
        return this;
    }

    @JsonProperty("canonical_id")
    public String getCanonicalId() {
        return canonicalId;
    }

    @JsonProperty("canonical_id")
    public void setCanonicalId(String canonicalId) {
        this.canonicalId = canonicalId;
    }

    public Recipe withCanonicalId(String canonicalId) {
        this.canonicalId = canonicalId;
        return this;
    }

    @JsonProperty("thumbnail_url")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @JsonProperty("thumbnail_url")
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Recipe withThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    @JsonProperty("brand_id")
    public Object getBrandId() {
        return brandId;
    }

    @JsonProperty("brand_id")
    public void setBrandId(Object brandId) {
        this.brandId = brandId;
    }

    public Recipe withBrandId(Object brandId) {
        this.brandId = brandId;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Recipe withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("num_servings")
    public Integer getNumServings() {
        return numServings;
    }

    @JsonProperty("num_servings")
    public void setNumServings(Integer numServings) {
        this.numServings = numServings;
    }

    public Recipe withNumServings(Integer numServings) {
        this.numServings = numServings;
        return this;
    }

    @JsonProperty("yields")
    public String getYields() {
        return yields;
    }

    @JsonProperty("yields")
    public void setYields(String yields) {
        this.yields = yields;
    }

    public Recipe withYields(String yields) {
        this.yields = yields;
        return this;
    }

    @JsonProperty("nutrition_visibility")
    public String getNutritionVisibility() {
        return nutritionVisibility;
    }

    @JsonProperty("nutrition_visibility")
    public void setNutritionVisibility(String nutritionVisibility) {
        this.nutritionVisibility = nutritionVisibility;
    }

    public Recipe withNutritionVisibility(String nutritionVisibility) {
        this.nutritionVisibility = nutritionVisibility;
        return this;
    }

    @JsonProperty("show")
    public Show getShow() {
        return show;
    }

    @JsonProperty("show")
    public void setShow(Show show) {
        this.show = show;
    }

    public Recipe withShow(Show show) {
        this.show = show;
        return this;
    }

    @JsonProperty("approved_at")
    public Integer getApprovedAt() {
        return approvedAt;
    }

    @JsonProperty("approved_at")
    public void setApprovedAt(Integer approvedAt) {
        this.approvedAt = approvedAt;
    }

    public Recipe withApprovedAt(Integer approvedAt) {
        this.approvedAt = approvedAt;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Recipe withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("brand")
    public Object getBrand() {
        return brand;
    }

    @JsonProperty("brand")
    public void setBrand(Object brand) {
        this.brand = brand;
    }

    public Recipe withBrand(Object brand) {
        this.brand = brand;
        return this;
    }

    @JsonProperty("tags")
    public List<Tag> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Recipe withTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    @JsonProperty("cook_time_minutes")
    public Object getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    @JsonProperty("cook_time_minutes")
    public void setCookTimeMinutes(Object cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    public Recipe withCookTimeMinutes(Object cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Recipe withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return "\n Recipe { " +
                "\n nutrition=" + nutrition +
                "\n, sections=" + sections +
                "\n, buzzId=" + buzzId +
                "\n, createdAt=" + createdAt +
                "\n, prepTimeMinutes=" + prepTimeMinutes +
                "\n, originalVideoUrl=" + originalVideoUrl +
                "\n, beautyUrl=" + beautyUrl +
                "\n, inspiredByUrl=" + inspiredByUrl +
                "\n, seoTitle='" + seoTitle + '\'' +
                "\n, tipsAndRatingsEnabled=" + tipsAndRatingsEnabled +
                "\n, totalTimeMinutes=" + totalTimeMinutes +
                "\n, videoUrl=" + videoUrl +
                "\n, compilations=" + compilations +
                "\n, facebookPosts=" + facebookPosts +
                "\n, id=" + id +
                "\n, renditions=" + renditions +
                "\n, language='" + language + '\'' +
                "\n, slug='" + slug + '\'' +
                "\n, videoId=" + videoId +
                "\n, instructions=" + instructions +
                "\n, topics=" + topics +
                "\n, isShoppable=" + isShoppable +
                "\n, draftStatus='" + draftStatus + '\'' +
                "\n, updatedAt=" + updatedAt +
                "\n, videoAdContent=" + videoAdContent +
                "\n, country='" + country + '\'' +
                "\n, isOneTop=" + isOneTop +
                "\n, keywords='" + keywords + '\'' +
                "\n, promotion='" + promotion + '\'' +
                "\n, servingsNounPlural='" + servingsNounPlural + '\'' +
                "\n, servingsNounSingular='" + servingsNounSingular + '\'' +
                "\n, showId=" + showId +
                "\n, thumbnailAltText='" + thumbnailAltText + '\'' +
                "\n, aspectRatio='" + aspectRatio + '\'' +
                "\n, totalTimeTier=" + totalTimeTier +
                "\n, userRatings=" + userRatings +
                "\n, credits=" + credits +
                "\n, canonicalId='" + canonicalId + '\'' +
                "\n, thumbnailUrl='" + thumbnailUrl + '\'' +
                "\n, brandId=" + brandId +
                "\n, description='" + description + '\'' +
                "\n, numServings=" + numServings +
                "\n, yields='" + yields + '\'' +
                "\n, nutritionVisibility='" + nutritionVisibility + '\'' +
                "\n, show=" + show +
                "\n, approvedAt=" + approvedAt +
                "\n, name='" + name + '\'' +
                "\n, brand=" + brand +
                "\n, tags=" + tags +
                "\n, cookTimeMinutes=" + cookTimeMinutes +
                "\n, additionalProperties=" + additionalProperties +
                '}';
    }
}
